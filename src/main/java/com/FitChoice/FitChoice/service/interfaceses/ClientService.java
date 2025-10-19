package com.FitChoice.FitChoice.service.interfaceses;

import com.FitChoice.FitChoice.model.dto.ClientCreateDto;
import com.FitChoice.FitChoice.model.dto.ClientResponseDto;
import com.FitChoice.FitChoice.model.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Client createClient(Client client);

    List<Client> findAllClients();

    Optional<Client> findClientById(Long id);

    void deleteClientById(Long id);

    Client toEntity(ClientCreateDto dto);

    ClientCreateDto toDto(Client client);

    ClientResponseDto toResponseDto(Client client);

}
