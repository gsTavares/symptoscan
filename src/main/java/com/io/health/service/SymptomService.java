package com.io.health.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.io.health.entity.dto.SymptomDTO;
import com.io.health.repository.LangRepository;
import com.io.health.repository.SymptomLangRepository;
import com.io.health.service.util.ApiResponse;

@Service
public class SymptomService {

    

    @Autowired
    private LangRepository langRepository;

    @Autowired
    private SymptomLangRepository symptomLangRepository;

    @Autowired
    private ApiResponse<SymptomDTO> apiResponse;

    public ResponseEntity<ApiResponse<Collection<SymptomDTO>>> list(String description, Long idLang,
            Pageable pageable) {
        langRepository.findById(idLang).orElseThrow(() -> new RuntimeException("language not found for id: " + idLang));

        if (description == null) {
            List<SymptomDTO> list = symptomLangRepository.findByLangId(idLang, pageable)
                    .stream()
                    .map(sLang -> new SymptomDTO(sLang.getSymptom()))
                    .collect(Collectors.toList());
            return !list.isEmpty() ? apiResponse.okGet(list, "symptom list returned successfully!")
                    : apiResponse.notFoundGet("symptom list not found!");
        } else {
            List<SymptomDTO> list = symptomLangRepository
                    .findByLangIdAndDescriptionContainsIgnoreCase(idLang, description, pageable)
                    .stream()
                    .map(sLang -> new SymptomDTO(sLang.getSymptom()))
                    .collect(Collectors.toList());
            return !list.isEmpty() ? apiResponse.okGet(list, "symptom list returned successfully!")
                    : apiResponse.notFoundGet("symptom list not found!");
        }
    } 

}
