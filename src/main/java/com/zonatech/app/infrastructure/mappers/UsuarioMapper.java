package com.zonatech.app.infrastructure.mappers;

import com.zonatech.app.domain.models.Usuario;
import com.zonatech.app.infrastructure.dto.request.usuario.UsuarioRegisterRequestDTO;
import com.zonatech.app.infrastructure.dto.response.UsuarioByEmailDTOResponse;
import com.zonatech.app.infrastructure.entities.UsuarioEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EvaluacionEstudianteMapper.class})
public interface UsuarioMapper {

    Usuario toModel(UsuarioEntity usuarioEntity);

    UsuarioEntity toEntity(Usuario usuario);

    List<Usuario> toModel(List<UsuarioEntity> usuarioEntities);

    Usuario toModel(UsuarioRegisterRequestDTO usuarioRegisterRequestDTO);

    UsuarioByEmailDTOResponse toDTOUsuarioByEmailResponse(Usuario usuario);

}
