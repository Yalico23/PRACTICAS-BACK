package com.zonatech.app.infrastructure.mappers;

import com.zonatech.app.domain.models.EvaluacionEstudiante;
import com.zonatech.app.infrastructure.dto.request.evaluacionEstudiante.RequestDtoCrearEvaluacionEstudiante;
import com.zonatech.app.infrastructure.dto.response.mentores.ResponseDtoEstudianteRespuesta;
import com.zonatech.app.infrastructure.entities.EvaluacionEstudianteEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses= {RespuestaEstudianteMapper.class})
public interface EvaluacionEstudianteMapper {

    EvaluacionEstudiante toModel (RequestDtoCrearEvaluacionEstudiante requestDtoCrearEvaluacionEstudiante);
    @AfterMapping
    default void mapRespuestas(
            RequestDtoCrearEvaluacionEstudiante dto,
            @MappingTarget EvaluacionEstudiante evaluacionEstudiante) {
        evaluacionEstudiante.setRespuestaEstudiantes(dto.getRespuestaEstudiantes());
    };

    ResponseDtoEstudianteRespuesta toResponseDto(EvaluacionEstudiante evaluacionEstudiante);

    EvaluacionEstudianteEntity toEntity (EvaluacionEstudiante model);
    @AfterMapping
    default void establecerRelacionesBidireccionales(
            EvaluacionEstudiante ignoredModel,
            @MappingTarget EvaluacionEstudianteEntity entity) {
        if (entity.getRespuestaEstudiantes() != null) {
            entity.getRespuestaEstudiantes().forEach(respuesta -> {
                respuesta.setEvaluacionEstudiante(entity);
            });
        }
    }

    EvaluacionEstudiante toModel(EvaluacionEstudianteEntity entity);
}
