package com.FitChoice.FitChoice.repository;

import com.FitChoice.FitChoice.model.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    List<Membership> findByClientId(Long id);

    void deleteAllMembershipsByClientId(Long id);

    List<Membership> findByClientIdOrderByEndDateDesc(Long id);
}

