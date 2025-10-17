package com.FitChoice.FitChoice.repository;

import com.FitChoice.FitChoice.model.entity.Membership;
import com.FitChoice.FitChoice.model.enums.MembershipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {


    List<Membership> findTop3ByClientIdAndTypeOrderByEndDateDesc(Long clientId, MembershipType type);

    @Query("SELECT COUNT(m) FROM Membership m " +
            "WHERE m.client.id = :clientId " +
            "AND m.type = :type " +
            "AND m.endDate >= :threeMonthsAgo")
    int countRecentMembershipsByClientAndType(
            @Param("clientId") Long clientId,
            @Param("type") MembershipType type,
            @Param("threeMonthsAgo") LocalDateTime threeMonthsAgo);

    List<Membership> findByClientId(Long id);

    int countRecentMembershipsByClient(Long id, LocalDate threeMonthsAgo);

}
