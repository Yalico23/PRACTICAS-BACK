package com.zonatech.app.domain.ports.input.entrevistaEstudiante;

import com.zonatech.app.domain.models.EntrevistaEstudiante;
import com.zonatech.app.domain.models.ResponseDtoEntrevistaPendientes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarEntrevistaEstudianteUseCase {
    EntrevistaEstudiante findById (Long id);
    Page<ResponseDtoEntrevistaPendientes> listEntrevistaEstudianteByIdEvaluacion
            (Long idEntrevista, String filter, Pageable pageable);
}
