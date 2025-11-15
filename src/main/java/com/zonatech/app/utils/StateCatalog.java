package com.zonatech.app.utils;

import lombok.Getter;

@Getter
public enum StateCatalog {
    PENDIENTE("Pendiente"),
    EN_PROGRESO("En Progreso"),
    COMPLETADO("Completado");

    private final String status;

    StateCatalog(String status) {
        this.status = status;
    }
}
