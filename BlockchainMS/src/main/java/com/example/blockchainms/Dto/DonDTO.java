package com.example.blockchainms.Dto;



import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

import java.util.UUID;

@Data
public class DonDTO {
    private String userId;
    private String organisationId;
    private Long id;
    private Double amount;
    private String paymentMethod;
    private String status;

    // Constructeur par défaut
    public DonDTO() {}

    // Vous pouvez également ajouter un constructeur personnalisé si vous en avez besoin
    public DonDTO(String userId, String organisationId, Long id, Double amount, String paymentMethod, String status) {
        this.userId = userId;
        this.organisationId = organisationId;
        this.id = id;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }
}