package com.vijay.CompletableFutureReactive.event;

import com.vijay.CompletableFutureReactive.entity.Patient;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PatientEventListener {
    @EventListener
    @Async
    public void handlePatientEvent(PatientEvent event) {
        Patient patient = event.getPatient();
        // Handle the event, e.g., send a notification
        System.out.println("Event received for patient: " + patient.getName());
    }
}
