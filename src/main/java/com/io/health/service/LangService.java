package com.io.health.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.io.health.entity.Lang;
import com.io.health.entity.Symptom;
import com.io.health.entity.SymptomLang;
import com.io.health.entity.dto.LangDTO;
import com.io.health.repository.LangRepository;
import com.io.health.repository.SymptomLangRepository;
import com.io.health.repository.SymptomRepository;
import com.io.health.service.util.ApiResponse;

@Service
public class LangService {

    @Autowired
    private LangRepository langRepository;

    @Autowired
    private SymptomRepository symptomRepository;

    @Autowired
    private SymptomLangRepository symptomLangRepository;

    @Autowired
    private ApiResponse<LangDTO> apiResponse;

    @Autowired
    private Environment env;

    public ResponseEntity<ApiResponse<LangDTO>> create(LangDTO dto) {
        Lang lang = new Lang(dto);
        Lang saveLang = langRepository.saveAndFlush(lang);
        LangDTO result = new LangDTO(saveLang);
        return apiResponse.ok(result, "created!");
    }

    public ResponseEntity<ApiResponse<Collection<LangDTO>>> list() {
        List<LangDTO> list = langRepository.findAll()
                .stream()
                .map(l -> new LangDTO(l))
                .collect(Collectors.toList());
        return !list.isEmpty() ? apiResponse.okGet(list, "language list returned successfull!")
            : apiResponse.notFoundGet("language list not found!");
    }

    @SuppressWarnings("unchecked")
    public ResponseEntity<ApiResponse<LangDTO>> translateSymptom(Long idSymptom,
            String targetLang) {

        final String SOURCE_LANGUAGE = "en";

        Lang tLang = langRepository.findByTag(targetLang)
                .orElseThrow(() -> new RuntimeException("language not found for tag: " + targetLang));

        Symptom s = symptomRepository.findById(idSymptom)
                .orElseThrow(() -> new RuntimeException("symptom not found for id: " + idSymptom));

        if (symptomLangRepository.findByLangIdAndSymptomId(s.getId(), tLang.getId()).isPresent()) {
            throw new RuntimeException(s.getDescription() + " was already translated to " + tLang.getDescription());
        }

        RestTemplate template = new RestTemplate();

        Class<ApiResponse<String>> responseClazz = (Class<ApiResponse<String>>) ApiResponse.<String>getType();
        ResponseEntity<ApiResponse<String>> tResponse = template.getForEntity(
                String.format(env.getProperty("translate.api.url"), s.getDescription(), SOURCE_LANGUAGE, tLang.getTag()),
                responseClazz);

        if (tResponse.getStatusCode().equals(HttpStatus.OK)) {
            SymptomLang sLang = new SymptomLang();
            sLang.setLang(tLang);
            sLang.setSymptom(s);
            sLang.setDescription(tResponse.getBody().getBody());
            symptomLangRepository.saveAndFlush(sLang);
            return apiResponse.ok(null, s.getDescription() + " translated sucessfully!");
        } else {
            return apiResponse.badRequest("error while translating " + s.getDescription());
        }
    }

}
