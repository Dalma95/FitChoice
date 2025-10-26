package com.FitChoice.FitChoice.controller;

import com.FitChoice.FitChoice.model.dto.MembershipCreateDto;
import com.FitChoice.FitChoice.model.dto.MembershipDto;
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
    public ResponseEntity<Membership> createMembership(@RequestBody MembershipCreateDto dto){
        return ResponseEntity.ok(membershipService.createMembership(dto));
    }

    @Operation(summary = "Renew membership")
    @PostMapping("/renew/{id}")
    public ResponseEntity<Membership> renewMembership(@PathVariable Long id){
        return ResponseEntity.ok(membershipService.renewMembership(id));
    }

    @Operation(summary = "Find membership by ID")
    @GetMapping("/{id}")
    public ResponseEntity<MembershipDto> getMembershipById(@PathVariable Long id){
        return membershipService.getMembershipById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Find membership by username")
    @GetMapping("/client/{username}")
    public ResponseEntity<List<MembershipDto>> getMembershipByClient(@PathVariable String username){
        return ResponseEntity.ok(membershipService.getMembershipsByClient(username));
    }

    @Operation(summary = "Update membership (type, trainer, nutritionist or fitness classes)")
    @PutMapping("/{username}/{membershipId}")
    public ResponseEntity<MembershipDto> updateMembership(
            @PathVariable String username,
            @PathVariable Long membershipId,
            @RequestBody MembershipCreateDto dto
    ) {
        Membership updated = membershipService.updateMembership(username, membershipId, dto);
        return ResponseEntity.ok(membershipService.toDto(updated));
    }

    @Operation(summary = "Delete all memberships for one user")
    @DeleteMapping({"/client/{id}"})
    public ResponseEntity<Void> deleteAllMembershipsByClientId(@PathVariable Long id){
        membershipService.deleteAllMembershipsByClientId(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete one membership for one user")
    @DeleteMapping("/{membershipId}/{username}")
    public ResponseEntity<Void> deleteByMembershipIdAndClientUserName(@PathVariable Long membershipId, @PathVariable String username){
        membershipService.deleteByMembershipIdAndClientUserName(membershipId,username);
        return ResponseEntity.noContent().build();
    }
}
