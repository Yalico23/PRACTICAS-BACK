package com.zonatech.app.domain.ports.input.evaluaciones;

import com.zonatech.app.domain.models.Evaluacion;
import com.zonatech.app.domain.models.EvaluacionResumen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ListarEvaluacionesUseCase {
    List<Evaluacion> findAllEvaluaciones();
    List<Evaluacion> findEvaluacionesByMentorId(Long idMentor);
    Page<Evaluacion> findEvaluacionesByMentorIdPageable(String email, String filtro, Pageable pageable);
    Page<EvaluacionResumen> listarEvaluacionesToEstudiantes
            (String emailEstudiante, String filtro, Pageable pageable);
    Evaluacion findEvaluacionById(Long idEvaluacion);
}
