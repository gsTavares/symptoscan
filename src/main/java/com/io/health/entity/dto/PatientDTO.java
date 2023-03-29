package com.io.health.entity.dto;

import com.io.health.entity.Patient;

public class PatientDTO extends PersonDTO{
    
    public PatientDTO() {
        super();
    }

    public PatientDTO(Patient patient) {
        super(patient);
    }

}
