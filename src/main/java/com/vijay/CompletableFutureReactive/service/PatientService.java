package com.vijay.CompletableFutureReactive.service;

import com.vijay.CompletableFutureReactive.entity.Patient;
import com.vijay.CompletableFutureReactive.event.PatientEvent;
import com.vijay.CompletableFutureReactive.repository.PatientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
@Log4j2
public class PatientService {
    private final PatientRepository repository;
    private final ApplicationEventPublisher publisher;

    @Async("asyncTaskExecutor")
    public CompletableFuture<List<Patient>> getAllPatients() {
        log.info("Fetching all patients");
        return CompletableFuture.supplyAsync(repository::findAll);
    }
    @Async("asyncTaskExecutor")
    public CompletableFuture<Patient> getPatient(Long id) {
        log.info("Fetching patient with ID: {}", id);
        return CompletableFuture.supplyAsync(() -> {
            return repository.findById(id).orElse(null);
        });
    }
    @Async("asyncTaskExecutor")
    public CompletableFuture<Patient> create(Patient patient) {
        log.info("Saving patient: {}", patient.getName());
        return CompletableFuture.supplyAsync(() -> {
            Patient savedPatient = repository.save(patient);
            publisher.publishEvent(new PatientEvent(this, savedPatient));
            return savedPatient;
        });
    }
    @Async("asyncTaskExecutor")
    public CompletableFuture<Void> deletePatient(Long id) {
        log.info("Deleting patient with ID: {}", id);
        return CompletableFuture.runAsync(() -> repository.deleteById(id));
    }
    @Async("asyncTaskExecutor")
    public CompletableFuture<Patient> updatePatient(Long id, Patient patientDetails) {
        log.info("Updating patient with ID: {}", id);
        return CompletableFuture.supplyAsync(() -> {
            Patient patient = repository.findById(id).orElse(null);
            if (patient != null) {
                patient.setName(patientDetails.getName());
                patient.setAddress(patientDetails.getAddress());
                patient.setPhoneNumber(patientDetails.getPhoneNumber());
                patient.setDob(patientDetails.getDob());
                Patient updatedPatient = repository.save(patient);
                publisher.publishEvent(new PatientEvent(this, updatedPatient));
                return updatedPatient;

            } else {
                return null;
            }
        });
    }
}
