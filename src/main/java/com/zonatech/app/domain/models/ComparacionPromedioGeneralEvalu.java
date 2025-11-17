package com.zonatech.app.domain.models;

public interface ComparacionPromedioGeneralEvalu {
    String getEvaluacion();
    Long getMiCalificacion();
    Double getCalificacionPromedioGeneral();
    Double getDiferenciaConPromedio();
    String getPosicionRelativa();
}
