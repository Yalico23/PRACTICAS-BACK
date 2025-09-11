package com.zonatech.app.infrastructure.repository;

import com.zonatech.app.domain.models.Roles;
import com.zonatech.app.domain.ports.output.RolesRepositoryPort;
import com.zonatech.app.infrastructure.entities.RolesEntity;
import com.zonatech.app.infrastructure.mappers.RolesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class RolesEntityAdapter implements RolesRepositoryPort {

    private final RolesMapper rolesMapper;
    private final RolesEntityRepository rolesEntityRepository;

    @Override
    public Optional<Roles> findByNombre(String nombre) {
        Optional<RolesEntity> rolesEntity = rolesEntityRepository.findByNombre(nombre);
        return rolesMapper.toModel(rolesEntity);
    }
}
