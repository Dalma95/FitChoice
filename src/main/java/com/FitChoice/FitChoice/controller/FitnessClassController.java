package com.FitChoice.FitChoice.controller;

import com.FitChoice.FitChoice.model.dto.FitnessClassDto;
import com.FitChoice.FitChoice.model.entity.FitnessClass;
import com.FitChoice.FitChoice.service.implementation.FitnessClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fitnessClasses")
public class FitnessClassController {

    @Autowired
    FitnessClassService fitnessClassService;

    @PostMapping
    public ResponseEntity<FitnessClass> createFitnessClass(@RequestBody FitnessClassDto dto){
        return ResponseEntity.ok(fitnessClassService.createFitnessClass(dto));
    }

    @GetMapping
    public ResponseEntity<List<FitnessClass>> findAllFitnessClasses(){
        return ResponseEntity.ok(fitnessClassService.findAllFitnessClasses());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<FitnessClass>> findClassById(@PathVariable Long id){
        return ResponseEntity.ok(fitnessClassService.findClassById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<FitnessClass> updateFitnessClass(@PathVariable Long id, @RequestBody FitnessClassDto dto){
        return ResponseEntity.ok(fitnessClassService.updateFitnessClass(id, fitnessClassService.toEntity(dto)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFitnessClass(@PathVariable Long id){
        fitnessClassService.deleteClassById(id);
        return ResponseEntity.noContent().build();
    }




}
