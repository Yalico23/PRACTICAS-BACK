package com.zonatech.app.application.usecases.entrevista;

import com.zonatech.app.domain.models.Entrevista;
import com.zonatech.app.domain.ports.input.entrevista.CambiarEstadoEntrevistaUseCase;
import com.zonatech.app.domain.ports.output.EntrevistaRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CambiarEstadoEntrevistaUseCaseImpl implements CambiarEstadoEntrevistaUseCase {

    private final EntrevistaRepositoryPort entrevistaRepositoryPort;

    @Override
    public Entrevista cambiarEstadoEntrevista(Long idEntrevista, boolean estadoPrevio) {

        Entrevista entrevista = entrevistaRepositoryPort.findById(idEntrevista).orElseThrow(
            () -> new IllegalArgumentException("Entrevista no encontrada con id: " + idEntrevista)
        );
        entrevista.setActivo(!entrevista.isActivo());
        return entrevistaRepositoryPort.save(entrevista);
    }
}
