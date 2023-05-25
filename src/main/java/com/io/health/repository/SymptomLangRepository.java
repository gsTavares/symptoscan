package com.io.health.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.io.health.entity.SymptomLang;

@Repository
public interface SymptomLangRepository extends JpaRepository<SymptomLang, Long> {
    
    List<SymptomLang> findByLangId(Long idLang, Pageable pageable);

    List<SymptomLang> findByLangIdAndDescriptionContainsIgnoreCase(Long idLang, String description, Pageable pageable);

    Optional<SymptomLang> findByLangIdAndSymptomId(Long idLang, Long idSymptom);

}
