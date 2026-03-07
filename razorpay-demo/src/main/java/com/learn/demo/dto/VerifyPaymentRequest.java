package com.learn.demo.dto;

public record VerifyPaymentRequest(
        String razorpayOrderId,

        String razorpayPaymentId,

        String razorpaySignature
) {
}
