package com.FitChoice.FitChoice.repository;

import com.FitChoice.FitChoice.model.dto.MembershipDto;
import com.FitChoice.FitChoice.model.entity.Membership;
import com.FitChoice.FitChoice.model.enums.MembershipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Long> {


    List<Membership> findTopLastThreeByClientIdAndTypeOrderByEndDateDesc(Long clientId, MembershipType type);

    @Query("SELECT COUNT(m) FROM Membership m WHERE m.client.id = :clientId AND m.endDate >= :threeMonthsAgo")
    int countRecentMembershipsByClient(
            @Param("clientId") Long clientId,
            @Param("threeMonthsAgo") LocalDateTime threeMonthsAgo);

    List<Membership> findByClientId(Long id);
}
