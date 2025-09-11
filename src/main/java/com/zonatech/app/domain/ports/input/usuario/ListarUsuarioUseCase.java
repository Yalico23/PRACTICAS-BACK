package com.zonatech.app.domain.ports.input.usuario;

import com.zonatech.app.domain.models.Usuario;

import java.util.List;

public interface ListarUsuarioUseCase {
    List<Usuario> listarUsuarios();
}
