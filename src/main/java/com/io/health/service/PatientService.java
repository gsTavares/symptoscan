package com.io.health.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.io.health.entity.Patient;
import com.io.health.entity.dto.PatientDTO;
import com.io.health.repository.PatientRepository;
import com.io.health.service.util.ApiResponse;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ApiResponse<PatientDTO> apiResponse;

    public ResponseEntity<ApiResponse<PatientDTO>> create(Patient patient) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        Patient patientCreated = patientRepository.saveAndFlush(patient);
        PatientDTO patientCreatedDTO = new PatientDTO(patientCreated);
        return apiResponse.ok(patientCreatedDTO, "created!"); 
    }

}
