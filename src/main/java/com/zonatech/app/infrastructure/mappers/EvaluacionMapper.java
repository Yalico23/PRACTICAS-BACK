package com.zonatech.app.infrastructure.mappers;

import com.zonatech.app.domain.models.Evaluacion;
import com.zonatech.app.domain.models.EvaluacionResumen;
import com.zonatech.app.infrastructure.dto.request.evaluacion.CrearEvaluacionRequestDTO;
import com.zonatech.app.infrastructure.dto.request.evaluacion.EditarEvaluacionRequestDTO;
import com.zonatech.app.infrastructure.dto.response.estudiante.EvaluacionByIdDTOResponse;
import com.zonatech.app.infrastructure.dto.response.mentores.CreateEvaluacionDtoResponse;
import com.zonatech.app.infrastructure.dto.response.mentores.ListEvaluacionesByMentoresDtoResponse;
import com.zonatech.app.infrastructure.dto.response.mentores.ResponseDtoEvaluacionesPendientesById;
import com.zonatech.app.infrastructure.entities.EvaluacionesEntity;
import com.zonatech.app.infrastructure.entities.PreguntaEntity;
import org.mapstruct.*;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface EvaluacionMapper {

    List<ResponseDtoEvaluacionesPendientesById> toPendientesById (List<Evaluacion> evaluacion);

    CreateEvaluacionDtoResponse toEvaluacionCreadaDtoResponse(Evaluacion evaluacion);

    Evaluacion toModelEvaluacion(CrearEvaluacionRequestDTO requestDTO);

    Evaluacion toModelEvaluacion(EditarEvaluacionRequestDTO evaluacionesEntity);

    Evaluacion toModelEvaluacion(EvaluacionesEntity evaluacionesEntity);

    default Optional<Evaluacion> toOptionalEvaluacion(Optional<EvaluacionesEntity> optionalEntity) {
        return optionalEntity.map(this::toModelEvaluacion);
    }

    List<Evaluacion> toEvaluacionList(List<EvaluacionesEntity> evaluacionesEntities);

    List<ListEvaluacionesByMentoresDtoResponse> toListEvaluacion(List<Evaluacion> evaluacion);

    // nota : ------------ Crear evaluciones inyectando en las demás clases

    @Mapping(source = "preguntas", target = "preguntas")
    EvaluacionesEntity toEvaluacionesEntity(Evaluacion evaluacion);

    @AfterMapping
    default void linkPreguntasToEvaluacion(@MappingTarget EvaluacionesEntity evaluacionEntity) {
        if (evaluacionEntity.getPreguntas() != null) {
            evaluacionEntity.getPreguntas().forEach(pregunta ->
                    pregunta.setEvaluacion(evaluacionEntity)
            );
        }
    }

    @AfterMapping
    default void setupPreguntaEntity(@MappingTarget PreguntaEntity preguntaEntity) {
        // Establecer relación con opciones de respuesta
        if (preguntaEntity.getOpcionRespuestas() != null) {
            preguntaEntity.getOpcionRespuestas().forEach(opcion ->
                    opcion.setPregunta(preguntaEntity)
            );
        }
    }
}
