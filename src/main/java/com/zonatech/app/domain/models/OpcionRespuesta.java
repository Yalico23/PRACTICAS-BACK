package com.zonatech.app.domain.models;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OpcionRespuesta {
    private Long id;
    private String opcionRespuesta;
    private boolean correcta;
}
