package com.io.health.service;

import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.io.health.entity.dto.SymptomDTO;
import com.io.health.repository.SymptomRepository;
import com.io.health.service.util.ApiResponse;

@Service
public class SymptomService {

    @Autowired
    private SymptomRepository symptomRepository;

    @Autowired
    private ApiResponse<SymptomDTO> apiResponse;

    @Autowired
    private ApiResponse<Object> genericResponse;

    public ResponseEntity<ApiResponse<Collection<SymptomDTO>>> list() {
        List<SymptomDTO> list = symptomRepository.findAll()
                .stream()
                .map(s -> new SymptomDTO(s))
                .collect(Collectors.toList());
        if (!list.isEmpty()) {
            return apiResponse.okGet(list.subList(0, 20), "symptom list returned successfully!");
        } else {
            return apiResponse.notFoundGet("symptom list is empty!");
        }
    }

    @SuppressWarnings("unchecked")
    public ResponseEntity<ApiResponse<Object>> translateTo(String sourceLang, String targetLang) {
        List<SymptomDTO> list = symptomRepository.findAll()
                .stream()
                .map(s -> new SymptomDTO(s))
                .collect(Collectors.toList());
        if (list.isEmpty()) {
            return genericResponse.notFound("symptom list is empty!");
        }
        Date start = new Date();
        List<SymptomDTO> translated = list.stream().parallel().map(element -> {
            RestTemplate restTemplate = new RestTemplate();
            SymptomDTO dto = new SymptomDTO();
            dto.setId(element.getId());
            try {
                String url = "http://localhost:3000/translate?text="
                        + URLEncoder.encode(element.getDescription(), "UTF-8")
                        + "&sl=" + URLEncoder.encode(sourceLang, "UTF-8")
                        + "&tl=" + URLEncoder.encode(targetLang, "UTF-8");
                Class<ApiResponse<String>> c = (Class<ApiResponse<String>>) ApiResponse
                        .<String>getType();
                ResponseEntity<ApiResponse<String>> response = restTemplate.getForEntity(url, c);
                if (response.getStatusCode().equals(HttpStatus.OK)) {
                    String translatedDescription = response.getBody().getBody();
                    dto.setDescription(translatedDescription);
                }
            } catch (Exception e) {
                dto.setDescription(element.getDescription());
                e.printStackTrace();
            }
            return dto;
        }).collect(Collectors.toList());

        Map<String, String> result = new HashMap<>();

        translated.stream()
                .forEach(s -> {
                    SymptomDTO original = list.stream()
                            .filter((sOriginal -> sOriginal.getId().equals(s.getId())))
                            .findFirst().get();
                    result.put(original.getDescription(), s.getDescription());
                });
        Date end = new Date();
        Long elapsedTime = end.getTime() - start.getTime();
        return genericResponse.ok(result, "symptom list translated successfully!. Elapsed time: " + elapsedTime);
    }

}
