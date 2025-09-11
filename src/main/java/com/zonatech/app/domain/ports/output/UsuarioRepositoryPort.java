package com.zonatech.app.domain.ports.output;

import com.zonatech.app.domain.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepositoryPort {
    Usuario save(Usuario usuario);
    List<Usuario> findAll();
    Optional<Usuario> findByEmail(String email);
    boolean existsByToken(String token);
    Optional<Usuario> findByToken(String token);}
