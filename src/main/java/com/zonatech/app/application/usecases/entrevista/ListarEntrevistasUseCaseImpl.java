package com.zonatech.app.application.usecases.entrevista;

import com.zonatech.app.domain.models.Entrevista;
import com.zonatech.app.domain.models.EntrevistaResumen;
import com.zonatech.app.domain.ports.input.entrevista.ListarEntrevistasUseCase;
import com.zonatech.app.domain.ports.output.EntrevistaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class ListarEntrevistasUseCaseImpl implements ListarEntrevistasUseCase {

    private final EntrevistaRepositoryPort entrevistaRepositoryPort;

    @Override
    public Page<Entrevista> findEntrevistasByMentorIdPageable(String email, String filtro, Pageable pageable) {
        return entrevistaRepositoryPort.findByMentorIdWithFilter(
                email,
                filtro,
                pageable
        );
    }

    @Override
    public Entrevista findEntrevistaById(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("El id no puede ser nulo o menor o igual a cero");
        }
        return entrevistaRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrevista no encontrada con id: " + id));
    }

    @Override
    public Page<EntrevistaResumen> listarEntrevistasEstudiante(String email, String filtro, Pageable pageable) {
        return entrevistaRepositoryPort.EntrevistaResumen(
                email,
                filtro,
                pageable
        );
    }

    @Override
    public List<Entrevista> listByMentorEmail(String email) {
        return entrevistaRepositoryPort.findByMentorEmail(email);
    }
}
