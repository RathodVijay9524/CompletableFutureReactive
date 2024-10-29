package com.vijay.CompletableFutureReactive.event;

import com.vijay.CompletableFutureReactive.entity.Patient;
import org.springframework.context.ApplicationEvent;

public class PatientEvent extends ApplicationEvent {
    private Patient patient;

    public PatientEvent(Object source, Patient patient) {
        super(source);
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }
}
