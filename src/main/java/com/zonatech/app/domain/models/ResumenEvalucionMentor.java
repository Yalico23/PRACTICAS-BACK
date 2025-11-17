package com.zonatech.app.domain.models;

public interface ResumenEvalucionMentor {
    String getEvaluacion();
    String getDescripcion();
    Long getTotalEstudiantesAsignados();
    Double getCalificacionPromedio();
    Long getCalificacionMinima();
    Long getCalificacionMaxima();
    Double getCompletados();
}
