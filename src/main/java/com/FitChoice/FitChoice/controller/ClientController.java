package com.FitChoice.FitChoice.controller;

import com.FitChoice.FitChoice.model.dto.ClientDto;
import com.FitChoice.FitChoice.model.entity.Client;
import com.FitChoice.FitChoice.service.interfaceses.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody ClientDto dto){
        return ResponseEntity.ok(clientService.createClient(clientService.toEntity(dto)));
    }

    @GetMapping
    public ResponseEntity<List<Client>> findAllClients(){
        return ResponseEntity.ok(clientService.findAllClients());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Client>> findClientById(@PathVariable Long id){
        return ResponseEntity.ok(clientService.findClientById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable Long id){
        clientService.deleteClientById(id);
        return ResponseEntity.noContent().build();
    }
}
