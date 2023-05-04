package com.io.health.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.io.health.entity.Disease;
import com.io.health.entity.Symptom;
import com.io.health.entity.enumerated.DatasourceFileType;
import com.io.health.repository.DiseaseRepository;
import com.io.health.repository.SymptomRepository;
import com.io.health.service.util.ApiResponse;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class DatasourceService {

    private final String FILE_EXTENSION = ".csv";

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private SymptomRepository symptomRepository;

    @Autowired
    private ApiResponse<List<?>> apiResponse;

    public ResponseEntity<ApiResponse<List<?>>> feedDatabase(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        System.out.println("filename = " + fileName);
        if (!fileName.contains(FILE_EXTENSION)) {
            throw new RuntimeException("Invalid file extension ("
                    + fileName.split("\\.")[1] + ")");
        }
        if (!DatasourceFileType.isValidFileType(fileName)) {
            throw new RuntimeException("Invalid file name. Suported values are: "
                    + Arrays.asList(DatasourceFileType.values())
                            .stream()
                            .map(dft -> dft.getFileName())
                            .collect(Collectors.joining(",")));
        }

        DatasourceFileType table = DatasourceFileType.getDataSourceFileType(fileName);
        switch (table) {
            case DISEASE: {
                try {
                    List<Disease> diseases = this.parseCsv(file.getInputStream(), Disease.class);
                    diseaseRepository.saveAll(diseases);
                    diseaseRepository.removeDuplicated();
                    return apiResponse.ok(diseases, "Diseases saved sucessfull!");                  
                } catch(IOException ioe) {
                    throw new IOException("Error while persisting diseases: " + ioe.getMessage());
                }
            }
            case SYMPTOM: {
                try {
                    List<Symptom> symptoms = this.parseCsv(file.getInputStream(), Symptom.class);
                    symptomRepository.saveAll(symptoms);
                    return apiResponse.ok(symptoms, "Symptons saved sucessfull!");
                } catch(IOException ioe) {
                    throw new IOException("Error while persisting symptons" + ioe.getMessage());
                }
            }
            default: {
                return apiResponse.notFound("not implemented yet...");
            }
        }
    }

    private <T> List<T> parseCsv(InputStream is, Class<T> clazz) throws IOException {
        try (Reader r = new InputStreamReader(is)) {
            CsvToBean<T> c = new CsvToBeanBuilder<T>(r)
                    .withType(clazz)
                    .withSeparator(',')
                    .build();
            return c.parse();
        }
    }

}
