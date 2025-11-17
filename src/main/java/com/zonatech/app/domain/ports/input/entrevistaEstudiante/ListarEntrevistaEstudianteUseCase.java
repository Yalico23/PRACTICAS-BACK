package com.zonatech.app.domain.ports.input.entrevistaEstudiante;

import com.zonatech.app.domain.models.EntrevistaEstudiante;
import com.zonatech.app.domain.models.ResponseDtoEntrevistaPendientes;
import com.zonatech.app.domain.models.TopEntrevistasEstudiantes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ListarEntrevistaEstudianteUseCase {
    EntrevistaEstudiante findById (Long id);
    Page<ResponseDtoEntrevistaPendientes> listEntrevistaEstudianteByIdEvaluacion
            (Long idEntrevista, String filter, Pageable pageable);
    EntrevistaEstudiante findByIdEntrevistaAndIdEstudiante(Long idEntrevista, Long idEstudiante);

    List<TopEntrevistasEstudiantes> getTopEntrevistasEstudiantes(Long idMentor);
}
