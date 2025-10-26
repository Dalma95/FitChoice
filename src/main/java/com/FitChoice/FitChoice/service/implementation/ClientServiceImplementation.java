package com.FitChoice.FitChoice.service.implementation;

import com.FitChoice.FitChoice.model.dto.ClientDto;
import com.FitChoice.FitChoice.model.dto.ClientResponseDto;
import com.FitChoice.FitChoice.model.entity.Client;
import com.FitChoice.FitChoice.repository.ClientRepository;
import com.FitChoice.FitChoice.service.interfaceses.ClientService;
import com.FitChoice.FitChoice.service.interfaceses.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplementation implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    MembershipService membershipService;

    @Override
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<ClientResponseDto> findAllClients(){
        return clientRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClientDto> findClientById(Long id){
        return clientRepository.findById(id)
                .map(this::toDto);
    }

    @Override
    public Client updateClient(Long id, Client client) {
        Client clientFound = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
        clientFound.setUserName(client.getUserName());
        clientFound.setEmail(client.getEmail());
        return clientRepository.save(clientFound);
    }

    @Override
    public void deleteClientById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Client toEntity(ClientDto dto){
        Client client = new Client();
        client.setUserName(dto.getUserName());
        client.setEmail(dto.getEmail());
        return client;
    }

    public ClientDto toDto(Client client){
        ClientDto dto = new ClientDto();
        dto.setUserName(client.getUserName());
        dto.setEmail(client.getEmail());

        return dto;
    }

    public ClientResponseDto toResponseDto(Client client){
        ClientResponseDto dto = new ClientResponseDto();
        dto.setId(client.getId());
        dto.setName(client.getUserName());
        dto.setEmail(client.getEmail());

        if (client.getMemberships() != null){
            dto.setMemberships(
                    client.getMemberships()
                            .stream()
                            .map(membershipService :: toDto)
                            .collect(Collectors.toList())
            );
        }
        return dto;
    }
}
