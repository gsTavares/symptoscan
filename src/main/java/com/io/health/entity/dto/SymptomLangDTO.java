package com.io.health.entity.dto;

import com.io.health.entity.SymptomLang;

public class SymptomLangDTO {

    private Long id;
    private SymptomDTO symptom;
    private LangDTO lang;
    private String description;

    public SymptomLangDTO() {
    }

    public SymptomLangDTO(SymptomLang symptomLang) {
        this.id = symptomLang.getId();
        this.symptom = new SymptomDTO(symptomLang.getSymptom());
        this.lang = new LangDTO(symptomLang.getLang());
        this.description = symptomLang.getDescription();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SymptomDTO getSymptom() {
        return symptom;
    }

    public void setSymptom(SymptomDTO symptom) {
        this.symptom = symptom;
    }

    public LangDTO getLang() {
        return lang;
    }

    public void setLang(LangDTO lang) {
        this.lang = lang;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SymptomLangDTO other = (SymptomLangDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
