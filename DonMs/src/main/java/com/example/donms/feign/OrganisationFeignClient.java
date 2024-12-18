package com.example.donms.feign;

import com.example.donms.dto.OrganisationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "OrganisationMs",url = "http://localhost:8082/organisations")
public interface OrganisationFeignClient {
    @GetMapping("/{organisationId}")
    OrganisationDTO getOrganisationById(@PathVariable("organisationId") UUID organisationId);
}
