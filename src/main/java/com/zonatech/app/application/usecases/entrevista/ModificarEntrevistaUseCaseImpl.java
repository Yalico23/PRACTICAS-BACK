package com.zonatech.app.application.usecases.entrevista;

import com.zonatech.app.domain.models.Entrevista;
import com.zonatech.app.domain.ports.input.entrevista.ModificarEntrevistaUseCase;
import com.zonatech.app.domain.ports.output.EntrevistaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RequiredArgsConstructor
public class ModificarEntrevistaUseCaseImpl implements ModificarEntrevistaUseCase {

    private final EntrevistaRepositoryPort entrevistaRepositoryPort;

    @Override
    public Entrevista updateEntrevista(Entrevista entrevista) {

        Entrevista existingEntrevista = entrevistaRepositoryPort
                .findById(entrevista.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrevista not found"));

        existingEntrevista.setFechaCreacion(LocalDate.now());
        existingEntrevista.setActivo(false);
        existingEntrevista.setTitulo(entrevista.getTitulo());
        existingEntrevista.setDescripcion(entrevista.getDescripcion());
        existingEntrevista.setPreguntas(entrevista.getPreguntas());

        return entrevistaRepositoryPort.save(existingEntrevista);
    }
}
