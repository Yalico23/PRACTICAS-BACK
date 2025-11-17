package com.zonatech.app.domain.models;

public interface ComparacionMentores {
    String getMentorNombre();
    String getMentorApellidos();
    Long getEvaluacionesConMentor();
    Double getCalificacionPromedio();
    Long getCalificacionMinima();
    Long getCalificacionMaxima();
}
