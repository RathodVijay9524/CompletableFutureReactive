package com.vijay.CompletableFutureReactive.controller;

import com.vijay.CompletableFutureReactive.entity.Patient;
import com.vijay.CompletableFutureReactive.exception.ResourceNotFoundException;
import com.vijay.CompletableFutureReactive.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@AllArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    public final PatientService service;

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Patient>>> getAllPatient() {
        return service.getAllPatients().thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Patient>> getOnePatient(@PathVariable  Long id) {
        return service.getPatient(id).thenApply((patient) -> {
            if (patient == null) {
                throw new ResourceNotFoundException("Patient not found with ID: " + id);
            }
            return ResponseEntity.ok(patient);
        });
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<Patient>> createPatient(@RequestBody Patient patient) {
        return service.create(patient).thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deletePatient(@PathVariable Long id) {
        return service.deletePatient(id).thenApply((patient) -> ResponseEntity.noContent().build());
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<Patient>> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        return service.updatePatient(id,patient).thenApply((updatedPatient)->{
            if(updatedPatient==null){
                throw new ResourceNotFoundException("Patient not found with ID: " + id);
            }
            return ResponseEntity.ok(updatedPatient);
        });
    }


}
