import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Payment() {
  const [amount, setAmount] = useState(500);
  const navigate = useNavigate();

  async function handlePay() {
    // create order from payment
    const orderResponse = await fetch("http://localhost:8082/api/payments/create-order", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ amount})
    });

    const orderData = await orderResponse.json();

    // open razorpay checkout
    const options = {
      key: orderData.key,
      amount: orderData.amount,
      currency: orderData.currency,
      name: "John Doe",
      description: "Test Payment",
      order_id: orderData.orderId,
      handler: async function (response) {
        const verifyRes = await fetch("http://localhost:8082/api/payments/verify", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            razorpayOrderId: response.razorpay_order_id,
            razorpayPaymentId: response.razorpay_payment_id,
            razorpaySignature: response.razorpay_signature,
          })
        });

        const verifyData = await verifyRes.json();

        if (verifyData.success) {
          navigate(`/success?orderId=${response.razorpay_order_id}&paymentId=${response.razorpay_payment_id}`);
        } else {
          navigate(`/failure?orderId=${response.razorpay_order_id}`);
        }
      },
      modal: {
        ondismiss: async function () {
          await fetch("http://localhost:8082/api/payments/payment-failed", {
            method: "POST",
            headers: {
              "Content-Type": "application/json"
            },
            body: JSON.stringify({
              orderId: orderData.orderId,
              reason: "Checkout closed by user"
            })
          });
        }
      },
      prefill: {
        name: "Kiran Doe",
        email: "kiran@mail.com",
        contact: "9384746392"
      },
      theme: {
        color: "#3399cc"
      }
    };

    const rzp = new window.Razorpay(options);
    rzp.on("payment.failed", async function() {
      await fetch("http://localhost:8082/api/payments/payment-failed", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          orderId: orderData.orderId,
          reason: "Payment failed"
        })
      });
      navigate(`/failure?orderId=${orderData.orderId}`);
    });
    rzp.open();
  }

  return (
    <main>
      <h2>Razorpay Test payment</h2>
      <input
        type="number"
        value={amount}
        onChange={(e) => setAmount(Number(e.target.value))}
      />
      <button onClick={handlePay}>
        Pay now
      </button>
    </main>
  );
}