package com.example.donms.Controller;

import com.example.donms.Entity.Don;
import com.example.donms.Repository.DonRepository;
import com.example.donms.Service.DonService;
import com.example.donms.dto.BlockDTO;
import com.example.donms.dto.DonDTO;
import com.example.donms.dto.OrganisationDTO;
import com.example.donms.dto.UserDTO;
import com.example.donms.feign.BlockchainFeignClient;
import com.example.donms.feign.OrganisationFeignClient;
import com.example.donms.feign.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/dons")
@Slf4j
@EnableKafka
public class DonController {
    @Autowired
      private OrganisationFeignClient organisationFeignClient;
    @Autowired
      private UserFeignClient userFeignClient;
    @Autowired
    DonService donService;
    ///TEST REPOSITORY IN SIDE
    @Autowired
    private DonRepository donRepository;

    //zedt hna wahd partie dyal blockchain test
    @Autowired
    private BlockchainFeignClient blockchainFeignClient;
    @Autowired
    private KafkaTemplate<Object, DonDTO> kafkaTemplate;

    @PostMapping("/cree")
    public ResponseEntity<Object> createDon(@RequestBody DonDTO donDto) {
        try {
            // Vérifier si les données sont valides avant de faire appel aux services
            if (donDto == null || donDto.userId() == null || donDto.organisationId() == null) {
                return ResponseEntity.status(400).body("Données manquantes dans la requête.");
            }

            // Appels aux services FeignClients
            UserDTO userResponse = userFeignClient.getUserById(donDto.userId());
            if (userResponse == null) {
                return ResponseEntity.status(404).body("Utilisateur non trouvé.");
            }

            OrganisationDTO organisationResponse = organisationFeignClient.getOrganisationById(donDto.organisationId());
            if (organisationResponse == null) {
                return ResponseEntity.status(404).body("Organisation non trouvée.");
            }

            // Création du don
            Don don = donService.createdon(donDto);

            // Récupérer dernier bloc de la blockchain b FeignClient bach tkon 3ndna relation binathom
            BlockDTO lastBlock = blockchainFeignClient.getLastBlock();
            String previousHash = lastBlock != null ? lastBlock.hash() : "0";

            long timestamp = System.currentTimeMillis();
            String hash =  donService.calculateHash(donDto, previousHash, timestamp);

            BlockDTO newBlock = new BlockDTO(don.getDonId(), donDto.amount(), donDto.userId(), donDto.organisationId(), previousHash, timestamp, hash);
            System.out.println("Sending BlockDTO: " + newBlock);
            System.out.println("Sending BlockDTO: " + don);
            blockchainFeignClient.addBlock(newBlock);
            //kafka
            log.info("Starting to send message to Kafka...");
            kafkaTemplate.send("notificationtopic", donDto);
            log.info("la partie kafka dazet");

            // Retourner la réponse avec le don créé
            return ResponseEntity.ok(don);
        } catch (Exception e) {
            // Log de l'exception pour le débogage
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur interne : " + e.getMessage());
        }
    }
//http://localhost:8080/donms/api/dons/cree

     // {
     //   "userId": "5c22c426-53e2-4d80-9dcb-9e900641fe52",
    //     "organisationId": "a668e370-f95e-4a07-a26c-34f5ef81cfa6",
    //      "amount": 50.50,
    //        "paymentMethod": "hamzasafi",
    //     "status": "dazet"
    // }
//==================================================================================================================
//Consulter un Don par ID
     @GetMapping("/{id}")
     public ResponseEntity<?> getDonById(@PathVariable("id") UUID donId) {
         Don don = donService.DonId(donId);
         if (don != null) {
             return ResponseEntity.ok(don);  // Retourner le don trouvé
         } else {
             return ResponseEntity.status(HttpStatus.NOT_FOUND)  // Retourner une erreur 404 si le don n'existe pas
                     .body(String.format("Le don avec l'ID %s n'existe pas.", donId));
         }
     }
//=====================================================================================================================
//Mettre à jour un Don
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateDon(@PathVariable("id") UUID donId,@RequestBody DonDTO donDTO){
        Don don =donService.DonId(donId);
        if(don != null){
            don.setUserId(donDTO.userId());
            don.setOrganisationId(donDTO.organisationId());
            don.setAmount(donDTO.amount());
            don.setPaymentMethod(donDTO.paymentMethod());
            don.setStatus(donDTO.status());
            //Hna khdemt direct b repository
            donRepository.save(don);
            return ResponseEntity.ok("La mise à jour a bien été effectuée.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Le don avec l'ID %s n'existe pas.", donId));
    }
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//Supprimer un Don
@DeleteMapping("/delete/{id}")
public ResponseEntity<String> deleteDon(@PathVariable("id") UUID donId) {
    Don don = donService.DonId(donId);
    if (don != null) {
        donService.SupId(donId);
        return ResponseEntity.ok("La suppression a été effectuée avec succès.");
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(String.format("Le don avec l'ID %s n'existe pas.", donId));
}
//======================================================================================================================
// Consulter les Dons d'un Utilisateur
@GetMapping("/user/{userId}")
public ResponseEntity<List<DonDTO>> getDonsByUserId(@PathVariable("userId") UUID userId) {
    List<DonDTO> donDTOs = donService.getDonsByUserId(userId);
    if (!donDTOs.isEmpty()) {
        return ResponseEntity.ok(donDTOs);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Collections.emptyList());
}
//======================================================================================================================
//Consulter les Dons d'une Organisation

    @GetMapping("/organisation/{organisationId}")
    public ResponseEntity<List<DonDTO>> getDonsByOrganisationId(@PathVariable("organisationId") UUID organisationId){
        List<DonDTO> donDTOs =donService.getDonsByOrganisationId(organisationId);
        if (!donDTOs.isEmpty()){
            return ResponseEntity.ok(donDTOs);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.emptyList());

    }
//======================================================================================================================
// Consulter l'Historique des Dons d'un Utilisateur avec des Statuts Filtrés
//feha probleme
@GetMapping("/user/{userId}/statuts/{EtatStatus}")
public ResponseEntity<List<DonDTO>> getDonsByUserIdAndStatus( @PathVariable("userId") UUID userId,
                                                              @PathVariable("status") String status){
      List<DonDTO>  donDTOS = donService.getDonsByUserIdAndStatus(userId, status);
    if (!donDTOS.isEmpty()) {
        return ResponseEntity.ok(donDTOS);
    }

    // Si aucun don n'est trouvé, on retourne un code 404 avec une liste vide
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
}

//======================================================================================================================
//Calculer le Montant Total des Dons pour une Organisation
@GetMapping("/organisation/{organisationId}/totalAmount")
public ResponseEntity<Double> getTotalDonAmountByOrganisationId(@PathVariable("organisationId") UUID organisationId) {
    double totalAmount = donService.getTotalDonAmountByOrganisationId(organisationId);

    // Retourner le montant total avec un code 200
    return ResponseEntity.ok(totalAmount);
}

//GET /api/organisation/123e4567-e89b-12d3-a456-426614174000/totalAmount

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//Envoyer un Message de Remerciement à un Donneur(kafka)
@PostMapping("/{donId}/remerciement")
public ResponseEntity<String> sendThankYouMessage(@PathVariable("donId") UUID donId) {
    Don don = donService.DonId(donId);

    if (don != null) {
        // Logique pour envoyer un message de remerciement
        String message = String.format("Merci %s pour votre généreux don de %.2f à l'organisation %s.",
                don.getUserId(), don.getAmount(), don.getOrganisationId());

        // khasss nchofo logic de kafka bach ne9dr nseftoh comme notification ou email
        // notificationService.send(don.getUserId(), message);

        return ResponseEntity.ok("Message de remerciement envoyé avec succès !");
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(String.format("Don avec l'ID %s introuvable.", donId));
}


    //======================================================================================================================
    @GetMapping("/all")
    public ResponseEntity<List<Don>> getAll() {
        List<Don> dons = donRepository.findAll();
        if (dons.isEmpty()) {
            return ResponseEntity.noContent().build(); // Renvoie 204 si aucun don n'est trouvé
        }
        return ResponseEntity.ok(dons);
    }

}
