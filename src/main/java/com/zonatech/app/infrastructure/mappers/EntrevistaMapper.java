package com.zonatech.app.infrastructure.mappers;

import com.zonatech.app.domain.models.Entrevista;
import com.zonatech.app.infrastructure.dto.request.entrevista.DTORequestEntrevistaUpdate;
import com.zonatech.app.infrastructure.dto.response.mentores.ResponseListarEntrevista;
import com.zonatech.app.infrastructure.entities.EntrevistaEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EntrevistaMapper {

    Entrevista toModel (DTORequestEntrevistaUpdate dtoRequestEntrevistaUpdate);
    Entrevista toModel(EntrevistaEntity entity);
    EntrevistaEntity toEntity (Entrevista model);
    ResponseListarEntrevista toDto (Entrevista entrevista);

    List<Entrevista> toModel (List<EntrevistaEntity> entrevistaEntities);

    @AfterMapping
    default void linkPreguntasToEntrevista(@MappingTarget EntrevistaEntity entrevistaEntity) {
        if (entrevistaEntity.getPreguntas() != null) {
            entrevistaEntity.getPreguntas().forEach(pregunta ->
                    pregunta.setEntrevista(entrevistaEntity)
            );
        }
    }
}
