package com.example.organisationms.Service;

import com.example.organisationms.DTO.OrgaDTO;
import com.example.organisationms.Entity.Organisation;
import com.example.organisationms.Exception.OrganisationAlreadyExistsException;
import com.example.organisationms.Repository.OrganisationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@AllArgsConstructor
@Transactional
public class OrganisationService {
    private static final Logger logger = LoggerFactory.getLogger(OrganisationService.class);

    private final OrganisationRepository organisationRepository;

    public void addOrganisation(Organisation organisation) {
        try {
            // Vérifier si l'organisation existe déjà
            if (organisationRepository.existsByName(organisation.getName())) {
                logger.warn("Tentative d'ajout d'une organisation existante : {}", organisation.getName());
                throw new OrganisationAlreadyExistsException("L'organisation avec ce nom existe déjà.");
            }

            // Sauvegarde de l'organisation dans la base de données
            organisationRepository.save(organisation);
            logger.info("Organisation '{}' ajoutée avec succès.", organisation.getName());

        } catch (Exception e) {
            logger.error("Erreur lors de l'ajout de l'organisation '{}': {}", organisation.getName(), e.getMessage());
            throw new RuntimeException("Une erreur est survenue lors de l'ajout de l'organisation.", e);
        }
    }

    }

