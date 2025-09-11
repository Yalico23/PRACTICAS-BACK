package com.zonatech.app.application.usecases.entrevista;

import com.zonatech.app.domain.ports.input.entrevista.EliminarEntrevistaUseCase;
import com.zonatech.app.domain.ports.output.EntrevistaEstudianteRepositoryPort;
import com.zonatech.app.domain.ports.output.EntrevistaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
public class EliminarEntrevistaUseCaseImpl implements EliminarEntrevistaUseCase {

    private final EntrevistaRepositoryPort entrevistaRepositoryPort;
    private final EntrevistaEstudianteRepositoryPort entrevistaEstudianteRepositoryPort;

    @Override
    public void elimnarEntrevista(Long idEntrevista) {
        entrevistaRepositoryPort
                .findById(idEntrevista)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrevista not found"));

        if(entrevistaEstudianteRepositoryPort.existsByEntrevistaId(idEntrevista)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entrevista is associated with students");
        }

        entrevistaRepositoryPort.deleteById(idEntrevista);
    }
}
