package com.FitChoice.FitChoice.controller;

import com.FitChoice.FitChoice.model.dto.ClientDto;
import com.FitChoice.FitChoice.model.dto.ClientResponseDto;
import com.FitChoice.FitChoice.model.entity.Client;
import com.FitChoice.FitChoice.service.interfaceses.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Operation(summary = "Create client")
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody ClientDto dto){
        return ResponseEntity.ok(clientService.createClient(clientService.toEntity(dto)));
    }

    @Operation(summary = "Find all clients")
    @GetMapping
    public ResponseEntity<List<ClientResponseDto>> findAllClients(){
        return ResponseEntity.ok(clientService.findAllClients());

    }

    @Operation(summary = "Find client by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> findClientById(@PathVariable Long id){
        return clientService.findClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update client")
    @PutMapping("/update/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody ClientDto dto){
        return ResponseEntity.ok(clientService.updateClient(id,clientService.toEntity(dto)));
    }


    @Operation(summary = "Delete client by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable Long id){
        clientService.deleteClientById(id);
        return ResponseEntity.noContent().build();
    }
}
