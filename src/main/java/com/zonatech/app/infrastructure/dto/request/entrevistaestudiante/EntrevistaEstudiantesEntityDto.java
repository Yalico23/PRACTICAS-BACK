package com.zonatech.app.infrastructure.dto.request.entrevistaestudiante;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.zonatech.app.infrastructure.entities.EntrevistaEstudiantesEntity}
 */
@Value
public class EntrevistaEstudiantesEntityDto implements Serializable {
    Long id;
    @NotNull
    @NotBlank
    String feedBack;
    @Min(1)
    @Max(20)
    int valoracion;
}