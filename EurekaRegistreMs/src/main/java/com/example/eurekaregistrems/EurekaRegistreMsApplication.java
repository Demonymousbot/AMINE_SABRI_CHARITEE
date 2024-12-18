package com.example.eurekaregistrems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaRegistreMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaRegistreMsApplication.class, args);
    }

}
