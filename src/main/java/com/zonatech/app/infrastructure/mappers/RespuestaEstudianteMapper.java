package com.zonatech.app.infrastructure.mappers;

import com.zonatech.app.domain.models.RespuestaEstudiante;
import com.zonatech.app.infrastructure.entities.RespuestaEstudianteEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RespuestaEstudianteMapper {

    RespuestaEstudiante toModel(RespuestaEstudianteEntity entity);
    RespuestaEstudianteEntity toEntity(RespuestaEstudiante model);
    List<RespuestaEstudiante> toModelList(List<RespuestaEstudianteEntity> entities);
    List<RespuestaEstudianteEntity> toEntityList(List<RespuestaEstudiante> models);
}
