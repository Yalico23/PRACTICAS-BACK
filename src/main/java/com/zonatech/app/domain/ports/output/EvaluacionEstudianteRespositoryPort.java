package com.zonatech.app.domain.ports.output;

import com.zonatech.app.domain.models.*;

import java.util.List;
import java.util.Optional;

public interface EvaluacionEstudianteRespositoryPort {
    EvaluacionEstudiante save(EvaluacionEstudiante evaluacionEstudiante);
    boolean existsByEvaluacionIdAndEstudianteId(Long evaluacionId, Long estudianteId);
    boolean existsByEvaluacionId(Long evaluacionId);
    List<Object[]> listEvaluacionesPendientesByEvaluacionId(Long evaluacionId);
    Optional<EvaluacionEstudiante> findById(Long id);
    EvaluacionEstudiante update (EvaluacionEstudiante evaluacionEstudiante);
    EvaluacionEstudiante findByEvaluacionIdAndEstudianteId(Long evaluacionId, Long estudianteId);
    PromedioGeneralDtoEstudiante getPromedioGeneralByIdEstudiante(Long idEstudiante);
    List<ComparacionPromedioGeneralEvalu>getComparacionPromedioGeneralEvalu(Long idEvaluacion);
    List<ProgresoMensualEstudiante> getProgresoMensualEstudiante(Long idEstudiante);
    List<ResumenEvalucionMentor> getResumenEvaluacionMentor(Long idMentor);
    List<MejorPeorDesempeno> getMejorPeorDesempenos(Long idMentor);
}
