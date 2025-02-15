package com.example.cr_motos_backend.repositories.access;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cr_motos_backend.model.access.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, UUID> {

}
