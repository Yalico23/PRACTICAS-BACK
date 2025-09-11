package com.zonatech.app.domain.ports.input.entrevista;

import com.zonatech.app.domain.models.Entrevista;

public interface ModificarEntrevistaUseCase {
    Entrevista updateEntrevista(Entrevista entrevista);
}
