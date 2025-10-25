package com.FitChoice.FitChoice.service.interfaceses;

import com.FitChoice.FitChoice.model.dto.MembershipCreateDto;
import com.FitChoice.FitChoice.model.dto.MembershipDto;
import com.FitChoice.FitChoice.model.dto.PaymentDto;
import com.FitChoice.FitChoice.model.entity.Membership;

import java.util.List;
import java.util.Optional;

public interface MembershipService {

    Membership createMembership(MembershipCreateDto dto);

    Membership renewMembership(Long membershipId);

    Optional<MembershipDto> getMembershipById(Long id);

    List<MembershipDto> getMembershipsByClient(String clientUserName);

    Membership updateMembership(String username, Long id, MembershipCreateDto dto);

    void deleteByMembershipIdAndClientUserName(Long id, String clientUserName );

    void deleteAllMembershipsByClientId(Long id);

    Membership toEntity(MembershipDto membershipDto);

    MembershipDto toDto (Membership membership);

    Membership toEntityFromCreateDto(MembershipCreateDto dto);
}
