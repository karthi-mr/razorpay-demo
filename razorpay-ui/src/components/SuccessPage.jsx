import { useLocation, Link } from "react-router-dom";

function SuccessPage() {
  const location = useLocation();
  const params = new URLSearchParams(location.search);

  const orderId = params.get("orderId");
  const paymentId = params.get("paymentId");

  return (
    <div style={{ padding: "40px" }}>
      <h1>Payment Successful</h1>
      <p><strong>Order ID:</strong> {orderId}</p>
      <p><strong>Payment ID:</strong> {paymentId}</p>
      <Link to="/">Go Back</Link>
    </div>
  );
}

export default SuccessPage;