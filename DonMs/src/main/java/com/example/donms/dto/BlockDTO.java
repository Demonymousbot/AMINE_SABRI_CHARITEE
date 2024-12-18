package com.example.donms.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record BlockDTO(UUID donId,
                       BigDecimal amount,
                       UUID senderUserId,
                       UUID receiverOrganisationId,
                       String previousHash,       // Hash du bloc précédent
                       long timestamp  ,
                       String hash        ) {
}
