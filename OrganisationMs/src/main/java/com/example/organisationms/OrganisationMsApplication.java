package com.example.organisationms;

import com.example.organisationms.Entity.Organisation;
import com.example.organisationms.Repository.OrganisationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
public class OrganisationMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrganisationMsApplication.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner(OrganisationRepository organisationRepository) {
        return args -> {
            // Création de la première organisation
            Organisation org1 = Organisation.builder()
                    .organisationId(UUID.randomUUID())
                    .name("Organisation A")
                    .description("Organisation dédiée à l'aide humanitaire.")
                    .contactInfo("contact@organisationA.org")
                    .website("https://www.organisationA.org")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            // Création de la deuxième organisation
            Organisation org2 = Organisation.builder()
                    .organisationId(UUID.randomUUID())
                    .name("Organisation B")
                    .description("Organisation qui soutient l'éducation.")
                    .contactInfo("contact@organisationB.org")
                    .website("https://www.organisationB.org")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            Organisation org3 = Organisation.builder()
                    .organisationId(UUID.randomUUID())
                    .name("Association Amine Sabri")
                    .description("Organisation à but non lucratif")
                    .contactInfo("contact@amine-sabri.org")
                    .website("http://www.amine-sabri.org")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();


            // Sauvegarde des organisations dans la base de données
            organisationRepository.save(org1);
            organisationRepository.save(org2);
            organisationRepository.save(org3);

            System.out.println("Les organisations ont été insérées avec succès.");
        };
}}
