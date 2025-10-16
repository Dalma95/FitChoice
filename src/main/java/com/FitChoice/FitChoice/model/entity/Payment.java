package com.FitChoice.FitChoice.model.entity;

import com.FitChoice.FitChoice.model.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private LocalDateTime paymentDate;


    private PaymentStatus status = PaymentStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

}
