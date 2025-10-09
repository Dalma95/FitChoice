package com.FitChoice.FitChoice.service.implementation;

import com.FitChoice.FitChoice.model.entity.Client;
import com.FitChoice.FitChoice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl {

    @Autowired
    ClientRepository clientRepository;

    public Client createClient(Client client){
        return clientRepository.save(client);
    }
}
