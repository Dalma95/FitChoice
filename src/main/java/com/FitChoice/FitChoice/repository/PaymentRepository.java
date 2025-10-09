package com.FitChoice.FitChoice.repository;

import com.FitChoice.FitChoice.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
