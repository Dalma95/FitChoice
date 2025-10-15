package com.FitChoice.FitChoice.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClientDto {

    private String name;
    private String email;
    private String phoneNumber;
    private String userName;

    private List<MembershipSummaryDto> memberships;

}
