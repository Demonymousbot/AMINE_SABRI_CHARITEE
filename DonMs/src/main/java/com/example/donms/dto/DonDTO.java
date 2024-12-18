package com.example.donms.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record DonDTO(UUID userId,
                     UUID organisationId,
                     UUID id, BigDecimal amount,
                     String paymentMethod,
                     String status) {
}
