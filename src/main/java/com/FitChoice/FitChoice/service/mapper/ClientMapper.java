package com.FitChoice.FitChoice.service.mapper;

import com.FitChoice.FitChoice.model.dto.ClientDto;
import com.FitChoice.FitChoice.model.dto.MembershipSummaryDto;
import com.FitChoice.FitChoice.model.entity.Client;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientMapper {

    public ClientDto toDto(Client client){
        if(client == null) return null;
        ClientDto clientDto = new ClientDto();
        clientDto.setName(client.getName());
        clientDto.setEmail(client.getEmail());
        clientDto.setPhoneNumber(client.getPhoneNumber());
        clientDto.setUserName(client.getUserName());

        if(client.getMemberships() != null){
            List<MembershipSummaryDto> memberships = client.getMemberships().stream()
                    .map( m -> {
                        MembershipSummaryDto ms = new MembershipSummaryDto();
                        ms.setType(m.getType());
                        ms.setFinalPrice(m.getFinalPrice());
                        ms.setStartDate(m.getStartDate());
                        ms.setEndDate(m.getEndDate());
                        ms.setIsActive(m.getIsActive());
                        return ms;
                    }).toList();
            clientDto.setMemberships(memberships);
        }
        return clientDto;
    }

    public Client toEntity(ClientDto clientDto){
        if(clientDto == null) return null;
        Client client = new Client();
        client.setName(clientDto.getName());
        client.setEmail(clientDto.getEmail());
        client.setPhoneNumber(clientDto.getPhoneNumber());
        client.setUserName(clientDto.getUserName());
        return client;
    }
}
