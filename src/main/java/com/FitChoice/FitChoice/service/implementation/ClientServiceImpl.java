package com.FitChoice.FitChoice.service.implementation;

import com.FitChoice.FitChoice.model.dto.ClientDto;
import com.FitChoice.FitChoice.model.entity.Client;
import com.FitChoice.FitChoice.repository.ClientRepository;
import com.FitChoice.FitChoice.repository.MembershipRepository;
import com.FitChoice.FitChoice.repository.PaymentRepository;
import com.FitChoice.FitChoice.service.interfaceses.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

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
}
