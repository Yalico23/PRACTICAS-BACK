package com.zonatech.app.domain.ports.output;

import com.zonatech.app.domain.models.EntrevistaEstudiante;
import com.zonatech.app.domain.models.ResponseDtoEntrevistaPendientes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EntrevistaEstudianteRepositoryPort {
    boolean existsByEntrevistaId(Long id);
    EntrevistaEstudiante save (EntrevistaEstudiante entrevistaEstudiante);
    Optional<EntrevistaEstudiante> findById (Long id);
    Page<ResponseDtoEntrevistaPendientes> listEntrevistasPendientesByEvaluacionId
            (Long entrevistaId, String filter, Pageable pageable);
    EntrevistaEstudiante findByEntrevistaIdAndEstudianteId(Long entrevistaId, Long estudianteId);
    EntrevistaEstudiante update(EntrevistaEstudiante entrevistaEstudiante);
}
