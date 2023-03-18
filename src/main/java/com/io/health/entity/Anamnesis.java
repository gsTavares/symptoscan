package com.io.health.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class Anamnesis implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne
    private Disease disease;

    @Column
    private LocalDateTime generationDate;

    @Column
    @NotNull
    private Boolean familiarHistory;
    
    @Column
    @NotNull
    private Boolean psychosocialHistory;

    @Column
    private Boolean correct;

    public Anamnesis() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getGenerationDate() {
        return generationDate;
    }

    public void setGenerationDate(LocalDateTime generationDate) {
        this.generationDate = generationDate;
    }

    public Boolean getFamiliarHistory() {
        return familiarHistory;
    }

    public void setFamiliarHistory(Boolean familiarHistory) {
        this.familiarHistory = familiarHistory;
    }

    public Boolean getPsychosocialHistory() {
        return psychosocialHistory;
    }

    public void setPsychosocialHistory(Boolean psychosocialHistory) {
        this.psychosocialHistory = psychosocialHistory;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
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
        Anamnesis other = (Anamnesis) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
