package com.FitChoice.FitChoice.controller;

import com.FitChoice.FitChoice.model.dto.ClientCreateDto;
import com.FitChoice.FitChoice.model.dto.ClientResponseDto;
import com.FitChoice.FitChoice.model.entity.Client;
import com.FitChoice.FitChoice.service.interfaceses.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Operation(summary = "Create client")
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody ClientCreateDto dto){
        return ResponseEntity.ok(clientService.createClient(clientService.toEntity(dto)));
    }

    @Operation(summary = "Find all clients")
    @GetMapping
    public ResponseEntity<List<ClientResponseDto>> findAllClients(){
        List<Client> clients = clientService.findAllClients();

        List<ClientResponseDto> response = clients.stream()
                .map(clientService :: toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Find client by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ClientCreateDto> findClientById(@PathVariable Long id){
        return clientService.findClientById(id)
                .map(client -> ResponseEntity.ok(clientService.toDto(client)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete client by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable Long id){
        clientService.deleteClientById(id);
        return ResponseEntity.noContent().build();
    }
}
