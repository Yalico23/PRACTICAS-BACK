package com.zonatech.app.infrastructure.dto.request.entrevista;

import com.zonatech.app.domain.models.PreguntaEntrevista;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DTORequestEntrevistaUpdate {
    @NotNull
    private Long id;
    @NotBlank
    private String titulo;
    @NotBlank
    private String descripcion;
    @NotNull
    private List<PreguntaEntrevista> preguntas;

}
