package com.FitChoice.FitChoice.controller;

import com.FitChoice.FitChoice.model.dto.MembershipCreateDto;
import com.FitChoice.FitChoice.model.dto.MembershipDto;
import com.FitChoice.FitChoice.model.dto.PaymentDto;
import com.FitChoice.FitChoice.model.entity.Membership;
import com.FitChoice.FitChoice.service.interfaceses.MembershipService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
public class MembershipController {

    @Autowired
    MembershipService membershipService;

    @Operation(summary = "Create membership")
    @PostMapping
    public ResponseEntity<MembershipDto> createMembership(@RequestBody MembershipDto dto){
        return ResponseEntity.ok(membershipService.createMembership(dto));
    }

    @Operation(summary = "Renew membership")
    @PostMapping("/{id}/renew")
    public ResponseEntity<MembershipDto> renewMembership(@PathVariable Long membershipId){
        return ResponseEntity.ok(membershipService.renewMembership(membershipId));
    }

    @Operation(summary = "Update payment")
    @PutMapping("/payment")
    public ResponseEntity<MembershipDto> updatePayment(@RequestBody PaymentDto dto){
        return ResponseEntity.ok(membershipService.updatePayment(dto));
    }

    @Operation(summary = "Find membership by ID")
    @GetMapping("/{id}")
    public ResponseEntity<MembershipDto> getMembershipById(@PathVariable Long id){
        return ResponseEntity.ok(membershipService.getMembershipById(id));
    }

    @Operation(summary = "Find membership by username")
    @GetMapping("/client/{username}")
    public ResponseEntity<List<MembershipDto>> getMembershipByClient(@PathVariable String username){
        return ResponseEntity.ok(membershipService.getMembershipsByClient(username));
    }
}
