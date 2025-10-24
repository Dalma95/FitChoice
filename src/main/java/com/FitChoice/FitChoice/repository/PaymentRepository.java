package com.FitChoice.FitChoice.repository;

import com.FitChoice.FitChoice.model.entity.Membership;
import com.FitChoice.FitChoice.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByClientId(Long id);
}
