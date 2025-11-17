package com.zonatech.app.domain.models;

public interface TopEntrevistasEstudiantes {
    Long getEstudianteId();
    String getNombre();
    String getApellidos();
    String getEmail();
    Long getTotalSesiones();
    Long getSesionesCompletadas();
    Double getNotaPromedio();
}
