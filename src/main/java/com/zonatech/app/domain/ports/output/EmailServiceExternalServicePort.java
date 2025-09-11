package com.zonatech.app.domain.ports.output;

import java.util.concurrent.CompletableFuture;

public interface EmailServiceExternalServicePort {
    CompletableFuture<Void> mandarCorreo
            (String Subject, String FromEmail, String ToEmail, String plainText, String htmlText);
}
