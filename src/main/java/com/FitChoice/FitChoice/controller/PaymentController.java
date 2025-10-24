package com.FitChoice.FitChoice.controller;

import com.FitChoice.FitChoice.model.dto.PaymentDto;
import com.FitChoice.FitChoice.model.entity.Payment;
import com.FitChoice.FitChoice.service.interfaceses.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Operation(summary = "Update payment")
    @PutMapping("/update")
    public ResponseEntity<Payment> updatePayment(@RequestBody PaymentDto dto){
        return ResponseEntity.ok(paymentService.updatePayment(paymentService.toEntity(dto)));
    }

    @Operation(summary = "Find payment by id")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable Long id){
        return paymentService.getPaymentById(id)
                .map(ResponseEntity ::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Find all payments for one user by user name")
    @GetMapping("/{username}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByClient(@PathVariable String username){
        return ResponseEntity.ok(paymentService.getPaymentsByClient(username));
    }




}
