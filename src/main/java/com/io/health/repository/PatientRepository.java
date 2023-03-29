package com.io.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.io.health.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{
    
}
