package com.FitChoice.FitChoice.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClientResponseDto {

    private Long id;
    private String name;
    private String email;

    private List<MembershipDto> memberships;
}
