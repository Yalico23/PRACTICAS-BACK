package com.zonatech.app.infrastructure.repository;

import com.zonatech.app.infrastructure.entities.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesEntityRepository extends JpaRepository<RolesEntity, Long> {
    Optional<RolesEntity> findByNombre(String nombre);
}