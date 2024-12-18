package com.example.donms;

import com.example.donms.Entity.Don;
import com.example.donms.Repository.DonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootApplication
@EnableFeignClients
public class DonMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DonMsApplication.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner(DonRepository donationRepository) {
        return args -> {
            Don donation1 = Don.builder()
                    .userId(UUID.randomUUID())
                    .organisationId(UUID.randomUUID())
                    .amount(BigDecimal.valueOf(100.00))
                    .paymentMethod("carte")
                    .status("réussi")
                    .build();

            Don donation2 = new Don(UUID.randomUUID(), UUID.randomUUID(), BigDecimal.valueOf(50.50), "virement", "en attente");

            donationRepository.save(donation1);
            donationRepository.save(donation2);
            System.out.println("====================================");
            System.out.println(donation2);
            System.out.println("=====================================");
            System.out.println(donation1);
        };
}}
