package com.FitChoice.FitChoice.service.implementation;

import com.FitChoice.FitChoice.model.dto.ClientCreateDto;
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
    public List<Client> findAllClients(){
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findClientById(Long id){
        return clientRepository.findById(id);
    }

    @Override
    public void deleteClientById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Client toEntity(ClientCreateDto dto){
        Client client = new Client();
        client.setUserName(dto.getUserName());
        client.setEmail(dto.getEmail());
        return client;
    }

    public ClientCreateDto toDto(Client client){
        ClientCreateDto dto = new ClientCreateDto();
        dto.setUserName(client.getUserName());
        dto.setEmail(client.getEmail());

        return dto;
    }

    public ClientResponseDto toResponseDto(Client client){
        ClientResponseDto dto = new ClientResponseDto();
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
