package com.zonatech.app.domain.ports.output;


public interface IAServiceExternalServicePort {
    String llamarServicioIA(String prompt);
    String transcribeVideo(byte[] videoBytes, String filename);
}
