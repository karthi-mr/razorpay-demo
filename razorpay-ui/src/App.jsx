import { BrowserRouter, Route, Routes } from "react-router-dom";
import Payment from "./components/Payment.jsx";
import SuccessPage from "./components/SuccessPage.jsx";
import FailurePage from "./components/FailurePage.jsx";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Payment />} />
        <Route path="/success" element={<SuccessPage />} />
        <Route path="/failure" element={<FailurePage />} />
      </Routes>
    </BrowserRouter>
  );
}