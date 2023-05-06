package com.io.health.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.io.health.entity.dto.SymptomDTO;
import com.io.health.repository.SymptomRepository;
import com.io.health.service.util.ApiResponse;

@Service
public class SymptomService {
    
    @Autowired
    private SymptomRepository symptomRepository;

    @Autowired
    private ApiResponse<SymptomDTO> apiResponse;

    public ResponseEntity<ApiResponse<Collection<SymptomDTO>>> list() {
        List<SymptomDTO> list = symptomRepository.findAll()
            .stream()
            .map(s -> new SymptomDTO(s))
            .collect(Collectors.toList());
        if(!list.isEmpty()) {
            return apiResponse.okGet(list, "symptom list returned successfully!");
        } else {
            return apiResponse.notFoundGet("symptom list is empty!");
        }
    }

}
