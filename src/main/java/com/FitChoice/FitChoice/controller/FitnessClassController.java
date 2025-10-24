package com.FitChoice.FitChoice.controller;

import com.FitChoice.FitChoice.model.dto.FitnessClassDto;
import com.FitChoice.FitChoice.model.entity.FitnessClass;
import com.FitChoice.FitChoice.service.interfaceses.FitnessClassService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Create fitness class")
    @PostMapping
    public ResponseEntity<FitnessClass> createFitnessClass(@RequestBody FitnessClassDto dto){
        return ResponseEntity.ok(fitnessClassService.createFitnessClass(fitnessClassService.toEntity(dto)));
    }

    @Operation(summary = "Find all classes")
    @GetMapping
    public ResponseEntity<List<FitnessClassDto>> findAllFitnessClasses(){
        return ResponseEntity.ok(fitnessClassService.findAllFitnessClasses());
    }

    @Operation(summary = "Find class by ID")
    @GetMapping("/{id}")
    public ResponseEntity<FitnessClassDto> findClassById(@PathVariable Long id){
        return fitnessClassService.findClassById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update fitness class")
    @PutMapping("/{id}")
    public ResponseEntity<FitnessClass> updateFitnessClass(@PathVariable Long id, @RequestBody FitnessClassDto dto){
        return ResponseEntity.ok(fitnessClassService.updateFitnessClass(id, fitnessClassService.toEntity(dto)));
    }

    @Operation(summary = "Delete fitness class by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFitnessClass(@PathVariable Long id){
        fitnessClassService.deleteClassById(id);
        return ResponseEntity.noContent().build();
    }




}
