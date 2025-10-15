package com.FitChoice.FitChoice.repository;

import com.FitChoice.FitChoice.model.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Long> {


    Optional<Membership> findMembershipByName(String membershipName);
}
