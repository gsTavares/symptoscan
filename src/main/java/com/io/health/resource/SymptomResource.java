package com.io.health.resource;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.io.health.entity.dto.SymptomDTO;
import com.io.health.service.SymptomService;
import com.io.health.service.util.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/symptom", produces = { MediaType.APPLICATION_JSON_VALUE })
public class SymptomResource {

    @Autowired
    private SymptomService symptomService;

    @GetMapping
    @Operation(description = "List all symptoms available")
    public ResponseEntity<ApiResponse<Collection<SymptomDTO>>> list(
            @RequestParam(name = "description", required = false, defaultValue = "") String description,
            @RequestParam(name = "idLang", required = true) Long idLang,
            @PageableDefault(page = 0, size = 10, sort = {"description"}) Pageable page) {
        return symptomService.list(description, idLang, page);
    }

}