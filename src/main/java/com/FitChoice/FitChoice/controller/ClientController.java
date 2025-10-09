package com.FitChoice.FitChoice.controller;

import com.FitChoice.FitChoice.model.dto.ClientDto;
import com.FitChoice.FitChoice.model.entity.Client;
import com.FitChoice.FitChoice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDto> createClient(ClientDto dto){
        Client client = clientService.toEntity(dto);
        return ResponseEntity.ok(clientService.toDto(client));
    }

}