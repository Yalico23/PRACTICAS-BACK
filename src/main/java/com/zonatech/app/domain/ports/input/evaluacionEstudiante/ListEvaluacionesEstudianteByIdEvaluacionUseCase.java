package com.zonatech.app.domain.ports.input.evaluacionEstudiante;

import com.zonatech.app.domain.models.*;

import java.util.List;

public interface ListEvaluacionesEstudianteByIdEvaluacionUseCase {
    List<Object[]> listEvaluacionesEstudianteByIdEvaluacion(Long idEvaluacion);
    EvaluacionEstudiante findById(Long id);
    EvaluacionEstudiante findIdEvaluacionEstudiante(Long idEstudiante, Long idEvaluacion);
    List<ComparacionMentores> getComparacionMentores(Long idEstudiante);
    List<ComparacionPromedioGeneralEvalu>getComparacionPromedioGeneralEvalu(Long idEvaluacion);
    List<ProgresoMensualEstudiante> getProgresoMensualEstudiante(Long idEstudiante);
    List<ResumenEvalucionMentor> getResumenEvaluacionMentor(Long idMentor);
    List<MejorPeorDesempeno> getMejorPeorDesempenos(Long idMentor);
}