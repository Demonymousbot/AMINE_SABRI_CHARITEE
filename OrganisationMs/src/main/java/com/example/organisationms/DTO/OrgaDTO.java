package com.example.organisationms.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrgaDTO(
        UUID organisationId,
        String name,
        String description,
        String contactInfo,
        String website,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
