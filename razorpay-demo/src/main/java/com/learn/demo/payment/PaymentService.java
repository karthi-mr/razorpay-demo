package com.learn.demo.payment;

import com.learn.demo.payment.dto.CreateOrderRequest;
import com.learn.demo.payment.dto.PaymentFailedRequest;
import com.learn.demo.payment.dto.VerifyPaymentRequest;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository repository;

    @Value("${application.razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${application.razorpay.key.secret}")
    private String razorpayKeySecret;

    public Map<String, Object> createOrder(@NonNull CreateOrderRequest request) throws RazorpayException {
        log.info("Creating order...");
        RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", request.amount() * 100);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "receipt_" + System.currentTimeMillis());

        Order order = razorpayClient.orders.create(orderRequest);

        Payment payment = Payment.builder()
                .orderId(order.get("id"))
                .amount(order.get("amount"))
                .currency(order.get("currency"))
                .receipt(order.get("receipt"))
                .status(PaymentStatus.PENDING)
                .build();

        repository.save(payment);

        Map<String, Object> response = new HashMap<>();
        response.put("orderId", payment.getOrderId());
        response.put("amount", payment.getAmount());
        response.put("currency", payment.getCurrency());
        response.put("key", razorpayKeyId);
        return response;
    }

    public Map<String, Object> verifyPayment(@NonNull VerifyPaymentRequest request) throws RazorpayException {
        log.info("Verifying payment...");

        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", request.razorpayOrderId());
        options.put("razorpay_payment_id", request.razorpayPaymentId());
        options.put("razorpay_signature", request.razorpaySignature());

        boolean isValid = Utils.verifyPaymentSignature(options, razorpayKeySecret);

        Payment payment = repository.findPaymentByOrderId(request.razorpayOrderId())
                .orElseThrow(() -> new RuntimeException("Payment record not found!"));
        if (isValid) {
            payment.setPaymentId(request.razorpayPaymentId());
            payment.setSignature(request.razorpaySignature());
            payment.setStatus(PaymentStatus.SUCCESS);
            repository.save(payment);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            repository.save(payment);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", isValid);
        response.put("orderId", payment.getOrderId());
        return response;
    }

    public void markPaymentFailed(@NonNull PaymentFailedRequest request) {
        Payment payment = repository.findPaymentByOrderId(request.orderId())
                .orElseThrow(() -> new RuntimeException("Payment record not found"));

        payment.setStatus(PaymentStatus.FAILED);
        repository.save(payment);
    }
}
