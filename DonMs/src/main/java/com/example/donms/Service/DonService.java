package com.example.donms.Service;

import com.example.donms.Entity.Don;
import com.example.donms.Repository.DonRepository;
import com.example.donms.dto.BlockDTO;
import com.example.donms.dto.DonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DonService {
    @Autowired
    private DonRepository donRepository;

    public Don createdon(DonDTO donDto) {
        Don don =new Don();
        don.setDonId(UUID.randomUUID()); // Générer un identifiant unique pour le Don
        don.setUserId(donDto.userId());
        don.setOrganisationId(donDto.organisationId());
        don.setAmount(donDto.amount());
        don.setPaymentMethod(donDto.paymentMethod());
        don.setStatus(donDto.status());
        don.setDate(LocalDateTime.now()); // Enregistrer la date du don
        return donRepository.save(don);
    }

    public Don DonId(UUID donId) {
       Don don = donRepository.findById(donId).get();
       if(don != null){
           return don;
       }
       return null;
    }

    public void SupId(UUID donId) {
        donRepository.deleteById(donId);
    }



   public List<DonDTO> getDonsByUserId(UUID userId) {
        List<Don> dons = donRepository.findByUserId(userId);

        // Si des dons existent, les convertir en DonDTO
        if (!dons.isEmpty()) {
            return dons.stream()
                    .map(don -> new DonDTO(
                            don.getDonId(),
                            don.getUserId(),
                            don.getOrganisationId(),
                            don.getAmount(),
                            don.getPaymentMethod(),
                            don.getStatus()
                    ))
                    .collect(Collectors.toList());
        }

        // Si aucun don n'est trouvé, retourner une liste vide
        return Collections.emptyList();
    }

    public List<DonDTO> getDonsByOrganisationId(UUID organisationId) {
        // Récupérer la liste des dons à partir du repository
        List<Don> dons = donRepository.findByorganisationId(organisationId);

        // Si des dons sont trouvés, les transformer en DonDTO
        if (!dons.isEmpty()) {
            return dons.stream()
                    .map(don -> new DonDTO(
                            don.getDonId(),
                            don.getUserId(),
                            don.getOrganisationId(),
                            don.getAmount(),
                            don.getPaymentMethod(),
                            don.getStatus()
                    ))
                    .collect(Collectors.toList());
        }

        // Si aucun don n'est trouvé, retourner une liste vide
        return Collections.emptyList();
    }


    public List<DonDTO> getDonsByUserIdAndStatus(UUID userId, String status) {
        List<Don> dons = donRepository.findByUserIdAndStatus(userId, status);
        if (!dons.isEmpty()) {
            return dons.stream()
                    .map(don -> new DonDTO(
                            don.getDonId(),
                            don.getUserId(),
                            don.getOrganisationId(),
                            don.getAmount(),
                            don.getPaymentMethod(),
                            don.getStatus()
                    ))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }



    public double getTotalDonAmountByOrganisationId(UUID organisationId) {
        // Récupérer la liste des dons pour l'organisation donnée
        List<Don> dons = donRepository.findByorganisationId(organisationId);

        return dons.stream()
                .map(Don::getAmount)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
    }


    public String calculateHash(DonDTO block, String previousHash, long timestamp) {
        String data = block.id() + previousHash + block.amount() + block.userId() + block.organisationId() + timestamp;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error calculating hash", e);
        }
    }


}




//methode khass nerje3 nchod ach nbedel feha bach nkhedem gha fo9aniya bohedha moh db teste beha


