package com.zonatech.app.infrastructure.mappers;

import com.zonatech.app.domain.models.EntrevistaEstudiante;
import com.zonatech.app.infrastructure.dto.response.mentores.ResponseEntrevistaEstudiante;
import com.zonatech.app.infrastructure.entities.EntrevistaEstudiantesEntity;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface EntrevistaEstudianteMapper {
    EntrevistaEstudiante toModel(EntrevistaEstudiantesEntity entrevistaEstudiantesEntity);

    EntrevistaEstudiantesEntity toEntity(EntrevistaEstudiante entrevistaEstudiante);

    ResponseEntrevistaEstudiante toResponse (EntrevistaEstudiante entrevistaEstudiante);

    default Optional<EntrevistaEstudiante> toOptionalModel(Optional<EntrevistaEstudiantesEntity> entity) {
        return entity.map(this::toModel);
    }

    default Optional<EntrevistaEstudiantesEntity> toOptionalEntity(Optional<EntrevistaEstudiante> model) {
        return model.map(this::toEntity);
    }
}
