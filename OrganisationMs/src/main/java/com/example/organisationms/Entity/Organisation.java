package com.example.organisationms.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "`organisation`")
public class Organisation {
    @Id
    private UUID organisationId;
    private String name;
    private String description;
    private String contactInfo;
    private String website;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @PrePersist
    private void prePersist() {
        if (this.organisationId == null) {
            this.organisationId = UUID.randomUUID(); // Générer un nouvel UUID avant la persistance
        }
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now; // Mettre à jour createdAt à la création
        this.updatedAt = now; // Mettre à jour updatedAt à la création
    }

    // Constructeur pour initialiser les autres champs
    public Organisation(String name, String description, String contactInfo, String website) {
        this.name = name;
        this.description = description;
        this.contactInfo = contactInfo;
        this.website = website;
    }

}
