package com.io.health.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.io.health.entity.Patient;
import com.io.health.entity.dto.PatientDTO;
import com.io.health.service.PatientService;
import com.io.health.service.util.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/patient", produces = MediaType.APPLICATION_JSON_VALUE)
public class PatientResource {

    @Autowired
    private PatientService patientService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<PatientDTO>> create(@Valid @RequestBody Patient patient) {
        return patientService.create(patient);
    }

}
