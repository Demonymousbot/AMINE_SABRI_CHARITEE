package com.example.donms.feign;

import com.example.donms.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service", url = "http://localhost:8081/api/v1/users")
public interface UserFeignClient {
    @GetMapping("/{id}")
    UserDTO getUserById(@PathVariable("id") UUID id);
}

