package com.zonatech.app.domain.models;

public interface ProgresoMensualEstudiante {
    String getMesAnio();
    Long getEvaluacionesRealizadas();
    Double getCalificacionPromedio();
    Long getCalificacionMinima();
    Long getCalificacionMaxima();
}
