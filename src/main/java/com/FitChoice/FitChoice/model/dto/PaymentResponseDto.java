package com.FitChoice.FitChoice.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentResponseDto {

    private Double amount;
    private String status;
    private LocalDateTime paymentDate;
}
