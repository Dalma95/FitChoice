package com.FitChoice.FitChoice.service;

import com.FitChoice.FitChoice.model.dto.ClientDto;
import com.FitChoice.FitChoice.model.entity.Client;

public interface ClientService {

    public ClientDto createClient(ClientDto clientDto);

    public default Client toEntity(ClientDto clientDto){
        Client client = new Client();
        client.setName(clientDto.getName());
        client.setEmail(clientDto.getEmail());
        client.setPhoneNumber(clientDto.getPhoneNumber());
        return client;
    }

    public default ClientDto toDto(Client client){
        ClientDto dto = new ClientDto();
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setPhoneNumber(client.getPhoneNumber());
        return dto;
    }
}
