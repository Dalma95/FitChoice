package com.FitChoice.FitChoice.controller;

import com.FitChoice.FitChoice.model.dto.TrainerDto;
import com.FitChoice.FitChoice.model.entity.Trainer;
import com.FitChoice.FitChoice.service.interfaceses.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    @Autowired
    TrainerService trainerService;

    @Operation(summary = "Create trainer")
    @PostMapping
    public ResponseEntity<Trainer> createTrainer(TrainerDto dto){
        return ResponseEntity.ok(trainerService.createTrainer(trainerService.toEntity(dto)));
    }

    @Operation(summary = "Find all trainers")
    @GetMapping
    public ResponseEntity<List<TrainerDto>> findAllTrainers(){
        return ResponseEntity.ok(trainerService.findAllTrainers());
    }

    @Operation(summary = "Find trainer by ID")
    @GetMapping("/{id}")
    public ResponseEntity<TrainerDto> findTrainerById(@PathVariable Long id){
        return trainerService.findTrainerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update trainer")
    @PutMapping("/update/{id}")
    public ResponseEntity<Trainer> updateTrainer(@PathVariable Long id, @RequestBody TrainerDto dto){
        return ResponseEntity.ok(trainerService.updateTrainer(id, trainerService.toEntity(dto)));
    }

    @Operation(summary = "Delete trainer by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainerById(@PathVariable Long id){
        trainerService.deleteTrainerById(id);
        return ResponseEntity.noContent().build();
    }
}
