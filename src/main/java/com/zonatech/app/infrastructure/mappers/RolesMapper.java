package com.zonatech.app.infrastructure.mappers;

import com.zonatech.app.domain.models.Roles;
import com.zonatech.app.infrastructure.entities.RolesEntity;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface RolesMapper {

    RolesEntity toModel (Roles roles);
    Roles toEntity (RolesEntity rolesEntity);

    default Optional<Roles> toModel(Optional<RolesEntity> rolesEntityOpt) {
        return rolesEntityOpt.map(this::toEntity);
    }

    default Optional<RolesEntity> toEntity(Optional<Roles> rolesOpt) {
        return rolesOpt.map(this::toModel);
    }
}
