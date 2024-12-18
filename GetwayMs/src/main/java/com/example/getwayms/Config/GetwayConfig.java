// package com.example.getwayms.Config;

// import org.springframework.cloud.gateway.route.RouteLocator;
// import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class GetwayConfig {
//
//     @Bean
//     public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
//         return builder.routes()
//                 .route("r1", r -> r.path("/api/**")
//                         .uri("http://localhost:8081/"))
//                 .build();
//     }
// }
