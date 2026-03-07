import { useLocation, Link } from "react-router-dom";

function FailurePage() {
  const location = useLocation();
  const params = new URLSearchParams(location.search);

  const orderId = params.get("orderId");

  return (
    <div style={{ padding: "40px" }}>
      <h1>Payment Failed</h1>
      <p><strong>Order ID:</strong> {orderId}</p>
      <p>Please try again.</p>
      <Link to="/">Try Again</Link>
    </div>
  );
}

export default FailurePage;