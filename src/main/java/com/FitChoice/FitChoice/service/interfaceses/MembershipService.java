package com.FitChoice.FitChoice.service.interfaceses;

import com.FitChoice.FitChoice.model.dto.MembershipCreateDto;
import com.FitChoice.FitChoice.model.dto.MembershipDto;
import com.FitChoice.FitChoice.model.dto.PaymentDto;
import com.FitChoice.FitChoice.model.entity.Membership;

import java.util.List;

public interface MembershipService {

    MembershipDto createMembership(MembershipDto dto);

    MembershipDto renewMembership(Long membershipId);

    MembershipDto updatePayment(PaymentDto dto);

    MembershipDto getMembershipById(Long id);

    List<MembershipDto> getMembershipsByClient(String clientUserName);

    double calculateTotalPrice(Membership membership);



    Membership toEntity(MembershipDto membershipDto);

    MembershipDto toDto (Membership membership);
}
