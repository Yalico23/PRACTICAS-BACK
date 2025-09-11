package com.zonatech.app.domain.ports.input.usuario;

import com.zonatech.app.domain.models.Usuario;

public interface EncontrarUsuarioUseCase {
    Usuario buscarUsuarioEmail (String email);
}
