package com.FitChoice.FitChoice.service.interfaceses;

import com.FitChoice.FitChoice.model.dto.ClientDto;
import com.FitChoice.FitChoice.model.dto.ClientResponseDto;
import com.FitChoice.FitChoice.model.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Client createClient(Client client);

    List<ClientResponseDto> findAllClients();

    Optional<ClientDto> findClientById(Long id);

    Client updateClient(Long id, Client client);

    void deleteClientById(Long id);

    Client toEntity(ClientDto dto);

    ClientDto toDto(Client client);

    ClientResponseDto toResponseDto(Client client);

}
