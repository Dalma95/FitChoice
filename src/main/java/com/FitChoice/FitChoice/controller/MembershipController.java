package com.FitChoice.FitChoice.controller;

import com.FitChoice.FitChoice.model.dto.MembershipCreateDto;
import com.FitChoice.FitChoice.model.dto.MembershipDto;
import com.FitChoice.FitChoice.model.dto.PaymentDto;
import com.FitChoice.FitChoice.model.entity.Membership;
import com.FitChoice.FitChoice.service.interfaceses.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
public class MembershipController {

    @Autowired
    MembershipService membershipService;

    @PostMapping
    public ResponseEntity<MembershipDto> createMembership(@RequestBody MembershipDto dto){
        return ResponseEntity.ok(membershipService.createMembership(dto));
    }

    @PostMapping("/{id}/renew")
    public ResponseEntity<MembershipDto> renewMembership(@PathVariable Long membershipId){
        return ResponseEntity.ok(membershipService.renewMembership(membershipId));
    }
    @PutMapping("/payment")
    public ResponseEntity<MembershipDto> updatePayment(@RequestBody PaymentDto dto){
        return ResponseEntity.ok(membershipService.updatePayment(dto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<MembershipDto> getMembershipById(@PathVariable Long id){
        return ResponseEntity.ok(membershipService.getMembershipById(id));
    }
    @GetMapping("/client/{username}")
    public ResponseEntity<List<MembershipDto>> getMembershipByClient(@PathVariable String username){
        return ResponseEntity.ok(membershipService.getMembershipsByClient(username));
    }
}
