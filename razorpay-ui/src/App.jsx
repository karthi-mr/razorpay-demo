import { useState } from "react";

export default function App() {
  const [amount, setAmount] = useState(500);

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
          alert("Payment success and verified");
        } else {
          alert("Payment done, but verification failed");
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