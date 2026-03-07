package com.learn.demo.payment.dto;

public record VerifyPaymentRequest(
        String razorpayOrderId,

        String razorpayPaymentId,

        String razorpaySignature
) {
}
