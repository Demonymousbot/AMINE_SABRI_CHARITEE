package com.example.organisationms.Repository;

import com.example.organisationms.Entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;
@RepositoryRestResource
public interface OrganisationRepository extends JpaRepository<Organisation, UUID> {
    boolean existsByName(String name);
}
