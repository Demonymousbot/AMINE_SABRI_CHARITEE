package com.example.organisationms.Controller;

import com.example.organisationms.DTO.OrgaDTO;
import com.example.organisationms.Entity.Organisation;
import com.example.organisationms.Repository.OrganisationRepository;
import com.example.organisationms.Service.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organisations")
public class OrganisationController {
@Autowired
private OrganisationService organisationService;
@Autowired
private OrganisationRepository organisationRepository;

//======================================================================================================================
//Création d'une organisation
@PostMapping("/save")
    ResponseEntity<String> createOrganisation(@RequestBody @Validated Organisation organisation){
        try {
        organisationService.addOrganisation(organisation);
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(String.format("organisation '%s' a été ajoutée avec succès.", organisation.getName()));
        } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Une erreur est survenue lors de l'ajout de l'organisation.");
        }
        }
//======================================================================================================================
//Consulter une Organisation par ID
    @GetMapping("/all")
    public ResponseEntity<List<Organisation>> getALL(){
    List<Organisation> org = organisationRepository.findAll();
    return ResponseEntity.ok(org);
    }

        }



























