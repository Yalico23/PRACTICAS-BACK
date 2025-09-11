package com.zonatech.app.domain.ports.output;

import com.zonatech.app.domain.models.Roles;

import java.util.Optional;

public interface RolesRepositoryPort {
    Optional<Roles> findByNombre(String nombre);
}
