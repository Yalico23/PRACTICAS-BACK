package com.zonatech.app.application.usecases.evaluacionEstudiante;

import com.zonatech.app.domain.exceptions.EvaluacionNoEncontradaException;
import com.zonatech.app.domain.models.*;
import com.zonatech.app.domain.ports.input.evaluacionEstudiante.ListEvaluacionesEstudianteByIdEvaluacionUseCase;
import com.zonatech.app.domain.ports.output.EvaluacionEstudianteRespositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListEvaluacionesEstudianteByIdEvaluacionUseCaseImpl
        implements ListEvaluacionesEstudianteByIdEvaluacionUseCase {

    private final EvaluacionEstudianteRespositoryPort evaluacionEstudianteRespositoryPort;

    @Override
    public List<Object[]> listEvaluacionesEstudianteByIdEvaluacion(Long idEvaluacion) {

        if(idEvaluacion == null){
            throw new EvaluacionNoEncontradaException("El ID de la evaluación no puede ser nulo.");
        }
        return evaluacionEstudianteRespositoryPort.listEvaluacionesPendientesByEvaluacionId(idEvaluacion);
    }

    @Override
    public EvaluacionEstudiante findById(Long id) {
        return evaluacionEstudianteRespositoryPort.findById(id)
                .orElseThrow(() -> new EvaluacionNoEncontradaException("Evaluación no encontrada con ID: " + id));
    }

    @Override
    public EvaluacionEstudiante findIdEvaluacionEstudiante(Long idEstudiante, Long idEvaluacion) {
        return evaluacionEstudianteRespositoryPort.findByEvaluacionIdAndEstudianteId(idEvaluacion,idEstudiante);
    }

    @Override
    public PromedioGeneralDtoEstudiante getPromedioGeneralByIdEstudiante(Long idEstudiante) {
        return evaluacionEstudianteRespositoryPort.getPromedioGeneralByIdEstudiante(idEstudiante);
    }

    @Override
    public List<ComparacionPromedioGeneralEvalu> getComparacionPromedioGeneralEvalu(Long idEvaluacion) {
        return evaluacionEstudianteRespositoryPort.getComparacionPromedioGeneralEvalu(idEvaluacion);
    }

    @Override
    public List<ProgresoMensualEstudiante> getProgresoMensualEstudiante(Long idEstudiante) {
        return evaluacionEstudianteRespositoryPort.getProgresoMensualEstudiante(idEstudiante);
    }

    @Override
    public List<ResumenEvalucionMentor> getResumenEvaluacionMentor(Long idMentor) {
        return evaluacionEstudianteRespositoryPort.getResumenEvaluacionMentor(idMentor);
    }

    @Override
    public List<MejorPeorDesempeno> getMejorPeorDesempenos(Long idMentor) {
        return evaluacionEstudianteRespositoryPort.getMejorPeorDesempenos(idMentor);
    }
}
