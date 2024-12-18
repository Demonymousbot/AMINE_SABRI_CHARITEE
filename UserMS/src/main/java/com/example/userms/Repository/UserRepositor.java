package com.example.userms.Repository;

import com.example.userms.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;
@RepositoryRestResource
public interface UserRepositor extends JpaRepository<User, UUID> {
    //methode deja definie par defaut et moi je le active ca
    public User getUserByName(@Param("n") String name);
}
