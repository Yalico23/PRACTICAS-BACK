package com.zonatech.app.domain.ports.input.entrevista;

import com.zonatech.app.domain.models.Entrevista;

public interface CambiarEstadoEntrevistaUseCase {
    Entrevista cambiarEstadoEntrevista(Long  idEntrevista, boolean estadoPrevio);
}
