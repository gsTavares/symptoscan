package com.io.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.io.health.entity.Disease;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    
    @Transactional
    @Modifying
    @Query(value = "delete from disease d1 using disease d2 where d1.id < d2.id and d1.description = d2.description", nativeQuery = true)
    void removeDuplicated();

}
