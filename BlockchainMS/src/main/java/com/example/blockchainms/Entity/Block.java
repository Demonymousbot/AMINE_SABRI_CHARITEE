package com.example.blockchainms.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.nio.file.NoSuchFileException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long blockId;
    private UUID donId;
    private BigDecimal amount;
    private UUID senderUserId;
    private UUID receiverOrganisationId;
    private String previousHash;
    private String hash;
    private long timestamp;

    public Block(Long blockId, UUID donId, String previousHash, BigDecimal amount, UUID sender, UUID receiver) {
        this.blockId = blockId;
        this.donId = donId;
        this.previousHash = previousHash;
        this.amount = amount;
        this.senderUserId = sender;
        this.receiverOrganisationId = receiver;
        this.timestamp = System.currentTimeMillis();
        this.hash = calculateHash();
    }


    // Autres méthodes (calculateHash, mineBlock, etc.)
  public String calculateHash() {
      String Data = donId.toString() + previousHash + amount.toString() + senderUserId.toString() + receiverOrganisationId.toString() + timestamp;
      try {
          MessageDigest digest = MessageDigest.getInstance("SHA-256");
          byte[] hashBytes = digest.digest(Data.getBytes());
          StringBuilder hexString = new StringBuilder();
          for (byte b : hashBytes) {
              hexString.append(String.format("%02x", b));
          }
          return hexString.toString();
      } catch (NoSuchAlgorithmException e) {
          throw new RuntimeException("Error calculating hash", e);
      }
  }
    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            timestamp++;
            hash = calculateHash();
        }

    }
    public String getHash() {
        return hash;
    }


}

