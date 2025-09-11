package com.zonatech.app.application.usecases.entrevista;

import com.zonatech.app.domain.models.Entrevista;
import com.zonatech.app.domain.ports.input.entrevista.CrearEntrevistaUseCase;
import com.zonatech.app.domain.ports.output.EntrevistaRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
public class CrearEntrevistaUseCaseImpl implements CrearEntrevistaUseCase {

    private final EntrevistaRepositoryPort repositoryPort;

    @Override
    public Entrevista crearEntrevista(Entrevista entrevista) {
        if (entrevista == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La entrevista no puede ser nula");
        }
        entrevista.setId(null); // Asegurarse de que el ID sea nulo para una nueva creación
        entrevista.setFechaCreacion(LocalDate.now());
        entrevista.setActivo(false);
        return repositoryPort.save(entrevista);
    }
}
