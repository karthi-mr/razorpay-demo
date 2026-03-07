package com.learn.demo;

import com.learn.demo.dto.CreateOrderRequest;
import com.learn.demo.dto.VerifyPaymentRequest;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
public class PaymentController {

    @Value("${application.razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${application.razorpay.key.secret}")
    private String razorpayKeySecret;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) throws RazorpayException {
        log.info("Creating order...");
        RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", request.amount() * 100);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "receipt_" + System.currentTimeMillis());

        Order order = razorpayClient.orders.create(orderRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("orderId", order.get("id"));
        response.put("amount", order.get("amount"));
        response.put("currency", order.get("currency"));
        response.put("key", razorpayKeyId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody VerifyPaymentRequest request) throws RazorpayException {
        log.info("Verifying payment...");

        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", request.razorpayOrderId());
        options.put("razorpay_payment_id", request.razorpayPaymentId());
        options.put("razorpay_signature", request.razorpaySignature());

        boolean isValid = Utils.verifyPaymentSignature(options, razorpayKeySecret);

        Map<String, Object> response = new HashMap<>();
        response.put("success", isValid);
        response.put("message", isValid ? "Payment verified successfully" : "Invalid signature");

        return ResponseEntity.ok(response);
    }
}
