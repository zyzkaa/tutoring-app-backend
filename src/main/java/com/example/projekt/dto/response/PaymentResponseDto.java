package com.example.projekt.dto.response;

import com.example.projekt.model.Payment;

public record PaymentResponseDto(
        Payment payment,
        ShortUserResponseDto user
) {
}
