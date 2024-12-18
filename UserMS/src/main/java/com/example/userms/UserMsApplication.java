package com.example.userms;

import com.example.userms.Entity.User;
import com.example.userms.Repository.UserRepositor;

import lombok.Builder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
public class UserMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserMsApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserRepositor userRepository){
        return args -> {
            User user1 = User.builder()
                    .userId(UUID.randomUUID())
                    .name("Ahmed El Amrani")
                    .email("ahmed.amrani@example.com")
                    .phoneNumber("+212 6 12 34 56 78")
                    .role("Bénévole")
                    .address("45 Avenue des Forces Armées Royales, Casablanca")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            User user2 = User.builder()
                    .userId(UUID.randomUUID())
                    .name("Fatima Zahra Benali")
                    .email("fatima.benali@example.com")
                    .phoneNumber("+212 6 87 65 43 21")
                    .role("Donateur")
                    .address("123 Rue des Jardins, Marrakech")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            User user5 = User.builder()
                    .userId(UUID.randomUUID())
                    .name("Amine Sabri")
                    .email("amine.sabri@example.com")
                    .phoneNumber("+212600000000")
                    .role("Donateur")
                    .address("Rabat, Maroc")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            User user4 = User.builder()
                    .userId(UUID.randomUUID())
                    .name("Said El Hamdi")
                    .email("said.elhamdi@example.com")
                    .phoneNumber("+212633112233")
                    .role("Donateur")
                    .address("Tanger, Maroc")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            User user7 = User.builder()
                    .userId(UUID.randomUUID())
                    .name("Samira Boussouf")
                    .email("samira.boussouf@example.com")
                    .phoneNumber("+212677112233")
                    .role("Bénéficiaire")
                    .address("Rabat, Maroc")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            User user6 = User.builder()
                    .userId(UUID.randomUUID())
                    .name("Mohamed El Yousfi")
                    .email("mohamed.yousfi@example.com")
                    .phoneNumber("+212661223344")
                    .role("Organisateur")
                    .address("Agadir, Maroc")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();


            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user5);



            userRepository.save(user4);
            userRepository.save(user6);
            userRepository.save(user7);

            userRepository.findAll().forEach(user -> {
                System.out.println("++++++++++++++++++++++++++++");
                System.out.println(user.getUserId());
                System.out.println(user.getName());
                System.out.println(user.getEmail());

            });
    };
}}
