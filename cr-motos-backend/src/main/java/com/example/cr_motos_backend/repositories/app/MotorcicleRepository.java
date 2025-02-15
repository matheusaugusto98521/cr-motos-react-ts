package com.example.cr_motos_backend.repositories.app;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cr_motos_backend.model.app.Motorcicle;

@Repository
public interface MotorcicleRepository extends JpaRepository<Motorcicle, UUID> {

}
