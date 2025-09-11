package com.zonatech.app.application.usecases.evaluacion;

import com.zonatech.app.domain.exceptions.EvaluacionNoEncontradaException;
import com.zonatech.app.domain.models.Evaluacion;
import com.zonatech.app.domain.models.EvaluacionResumen;
import com.zonatech.app.domain.ports.input.evaluaciones.ListarEvaluacionesUseCase;
import com.zonatech.app.domain.ports.output.EvaluacionesRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class ListarEvaluacionesUseCaseImpl implements ListarEvaluacionesUseCase {

    private final EvaluacionesRepositoryPort evaluacionesRepositoryPort;

    @Override
    public List<Evaluacion> findAllEvaluaciones() {
        return evaluacionesRepositoryPort.findAllEvaluaciones();
    }

    @Override
    public List<Evaluacion> findEvaluacionesByMentorId(Long idMentor) {
        return evaluacionesRepositoryPort.findAllEvaluacionesById(idMentor);
    }

    @Override
    public Page<Evaluacion> findEvaluacionesByMentorIdPageable
            (String email, String filtro, Pageable pageable) {
        return evaluacionesRepositoryPort.findByMentorIdWithFilter(email, filtro, pageable);
    }

    @Override
    public Page<EvaluacionResumen> listarEvaluacionesToEstudiantes
            (String emailEstudiante, String filtro, Pageable pageable) {
        return evaluacionesRepositoryPort.findEvaluacionesResumenRaw(emailEstudiante, filtro, pageable);
    }

    @Override
    public Evaluacion findEvaluacionById(Long idEvaluacion) {
        return evaluacionesRepositoryPort.findById(idEvaluacion).orElseThrow(()-> new EvaluacionNoEncontradaException("Evaluacion no encontrada con el id : " + idEvaluacion));
    }

}
