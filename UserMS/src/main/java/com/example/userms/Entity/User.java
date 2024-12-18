package com.example.userms.Entity;

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
@Table(name = "`user`")
public class User {

    @Id
    private UUID userId; // Identifiant unique du user
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        if (this.userId == null) {
            this.userId = UUID.randomUUID(); // Générer un nouvel UUID avant la persistance
        }
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now; // Mettre à jour createdAt à la création
        this.updatedAt = now; // Mettre à jour updatedAt à la création
    }

    // Constructeur pour initialiser les autres champs
    public User(String name, String email, String phoneNumber, String role, String address) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.address = address;
    }
}
