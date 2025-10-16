package com.FitChoice.FitChoice.controller;

import com.FitChoice.FitChoice.model.dto.TrainerDto;
import com.FitChoice.FitChoice.model.entity.Trainer;
import com.FitChoice.FitChoice.service.interfaceses.TrainerService;
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

    @PostMapping
    public ResponseEntity<Trainer> createTrainer(TrainerDto dto){
        return ResponseEntity.ok(trainerService.createTrainer(trainerService.toEntity(dto)));
    }
    @GetMapping
    public ResponseEntity<List<Trainer>> findAllTrainers(){
        return ResponseEntity.ok(trainerService.findAllTrainers());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Trainer>> findTrainerById(@PathVariable Long id){
        return ResponseEntity.ok(trainerService.findTrainerById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainerById(@PathVariable Long id){
        trainerService.deleteTrainerById(id);
        return ResponseEntity.noContent().build();
    }
}
