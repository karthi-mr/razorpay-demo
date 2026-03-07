package com.learn.demo.payment.dto;

public record PaymentFailedRequest(
        String orderId,

        String reason
) {
}
