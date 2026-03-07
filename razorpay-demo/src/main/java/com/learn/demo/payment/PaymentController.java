package com.learn.demo.payment;

import com.learn.demo.payment.dto.CreateOrderRequest;
import com.learn.demo.payment.dto.PaymentFailedRequest;
import com.learn.demo.payment.dto.VerifyPaymentRequest;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) throws RazorpayException {
        return ResponseEntity.ok(service.createOrder(request));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody VerifyPaymentRequest request) throws RazorpayException {
        return ResponseEntity.ok(service.verifyPayment(request));
    }

    @PostMapping("/payment-failed")
    public ResponseEntity<String> paymentFailed(@RequestBody PaymentFailedRequest request) {
        service.markPaymentFailed(request);
        return ResponseEntity.ok("Payment marked as failed");
    }
}
