package com.io.health.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.io.health.entity.Lang;

@Repository
public interface LangRepository extends JpaRepository<Lang, Long>{
    
    Optional<Lang> findByTag(String tag);

}
