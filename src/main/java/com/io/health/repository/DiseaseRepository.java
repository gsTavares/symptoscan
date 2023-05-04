package com.io.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.io.health.entity.Disease;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    
}
