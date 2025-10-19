package com.FitChoice.FitChoice.repository;

import com.FitChoice.FitChoice.model.entity.Membership;
import com.FitChoice.FitChoice.model.enums.MembershipType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    List<Membership> findTop3ByClientIdAndTypeOrderByEndDateDesc(Long clientId, MembershipType type);

    List<Membership> findByClientId(Long id);

    int countByClient_IdAndEndDateAfter(Long clientId, LocalDate threeMonthsAgo);

    void deleteAllMembershipsByClientId(Long id);


    List<Membership> findByClientIdOrderByEndDateDesc(Long id);
}

