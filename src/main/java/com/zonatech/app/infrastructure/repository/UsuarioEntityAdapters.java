package com.zonatech.app.infrastructure.repository;

import com.zonatech.app.domain.models.Evaluacion;
import com.zonatech.app.domain.models.Usuario;
import com.zonatech.app.domain.ports.output.UsuarioRepositoryPort;
import com.zonatech.app.infrastructure.entities.UsuarioEntity;
import com.zonatech.app.infrastructure.mappers.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class UsuarioEntityAdapters implements UsuarioRepositoryPort {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioEntityRepository usuarioEntityRepository;


    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity usuarioEntity = usuarioMapper.toEntity(usuario);
        return usuarioMapper.toModel(usuarioEntityRepository.save(usuarioEntity));
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioMapper.toModel(usuarioEntityRepository.findAll());
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return Optional.ofNullable(usuarioMapper.toModel(usuarioEntityRepository.findByEmail(email)));
    }

    @Override
    public boolean existsByToken(String token) {
        return usuarioEntityRepository.existsByToken(token);
    }

    @Override
    public Optional<Usuario> findByToken(String token) {
        return Optional.ofNullable(usuarioMapper.toModel(usuarioEntityRepository.findByToken(token)));
    }
}
