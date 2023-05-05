package com.io.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.io.health.entity.Symptom;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Long>{
    
}
