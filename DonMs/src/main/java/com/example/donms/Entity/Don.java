package com.example.donms.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "`don`")
public class Don {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID donId; // Identifiant unique du don

    private UUID userId; // Référence à l'utilisateur qui effectue le don

    private UUID organisationId; // Référence à l'organisation recevant le don

    private BigDecimal amount; // Montant du don

    private LocalDateTime date; // Date du don

    private String paymentMethod; // Méthode de paiement (carte, virement, etc.)

    private String status;
    // "En attente", "Terminé", "Échoué", "Annulé",
    @PrePersist
    private void prePersist() {
        if (this.donId == null) {
            this.donId = UUID.randomUUID(); // Générer un nouvel UUID avant la persistance
        }
        this.date = LocalDateTime.now(); // Enregistre la date actuelle lors de la création du don
    }

    // Constructeur pour initialiser les autres champs
    public Don(UUID userId, UUID organisationId, BigDecimal amount, String paymentMethod, String status) {
        this.userId = userId;
        this.organisationId = organisationId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }
}
