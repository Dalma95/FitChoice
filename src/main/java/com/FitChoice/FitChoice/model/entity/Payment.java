package com.FitChoice.FitChoice.model.entity;

import com.FitChoice.FitChoice.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

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

    @OneToOne
    @JoinColumn(name ="membership_id" )
    private Membership membership;
}
