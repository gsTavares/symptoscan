package com.io.health.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.io.health.service.DatasourceService;
import com.io.health.service.util.ApiResponse;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping(value = "/datasource", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public class DatasourceResource {

    @Autowired
    private DatasourceService datasourceService;

    @PostMapping
    @Parameter(name = "csvFile", description = "csv file with all symptoms or diseases. acceptable file names are \"symptons.csv\" and \"diseases.csv\"", required = true)
    public ResponseEntity<ApiResponse<List<?>>> upload(@RequestParam("csvFile") MultipartFile file) throws Exception {
        return datasourceService.feedDatabase(file);
    }

}
