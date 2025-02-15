package com.example.cr_motos_backend.infra.security.model.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.example.cr_motos_backend.infra.security.model.Auth;

@Repository
public interface AuthRepository extends JpaRepository<Auth, UUID> {
    UserDetails findByUsername(String username);
}
