package com.io.health.resource;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.io.health.entity.dto.LangDTO;
import com.io.health.service.LangService;
import com.io.health.service.util.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/lang", produces = { MediaType.APPLICATION_JSON_VALUE })
public class LangResource {

    @Autowired
    private LangService langService;

    @PostMapping
    @Operation(description = "Create a new language")
    public ResponseEntity<ApiResponse<LangDTO>> create(@Valid @RequestBody LangDTO dto) {
        return langService.create(dto);
    }

    @GetMapping
    @Operation(description = "List all languages available")
    public ResponseEntity<ApiResponse<Collection<LangDTO>>> list() {
        return langService.list();
    }

    @PostMapping(value = "/translate-symptom")
    @Operation(description = "Saves a new translated version of a symptom")
    public ResponseEntity<ApiResponse<LangDTO>> translateSymptom(
            @RequestParam(name = "idSymptom", required = true) Long idSymptom,
            @RequestParam(name = "targetLang", required = true, defaultValue = "pt") String targetLang) {
        return langService.translateSymptom(idSymptom, targetLang);
    }

}
