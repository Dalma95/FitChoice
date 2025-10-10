package com.FitChoice.FitChoice.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentDto {

    private Double amount;
    private String status;
    private LocalDateTime paymentDate;
}
