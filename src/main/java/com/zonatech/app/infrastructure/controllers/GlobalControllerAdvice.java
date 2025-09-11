package com.zonatech.app.infrastructure.controllers;

import com.zonatech.app.domain.exceptions.CorreoNoEncontrado;
import com.zonatech.app.domain.exceptions.CorreoYaRegistradoException;
import com.zonatech.app.domain.exceptions.EvaluacionNoEncontradaException;
import com.zonatech.app.domain.exceptions.NoPuedeSerNull;
import com.zonatech.app.domain.models.ErrorResponse;
import com.zonatech.app.utils.ErrorCatalog;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CorreoNoEncontrado.class)
    public ErrorResponse handleCorreoNotFound(CorreoNoEncontrado ex) {
        return ErrorResponse.builder()
                .code(ErrorCatalog.CORREO_NOT_FOUND.getCode())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CorreoYaRegistradoException.class)
    public ErrorResponse handleCorreoYaExiste(CorreoYaRegistradoException ex) {
        return ErrorResponse.builder()
                .code(ErrorCatalog.CORREO_YA_EXISTE.getCode())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoPuedeSerNull.class)
    public ErrorResponse handleNoPuedeSerNull(NoPuedeSerNull ex) {
        return ErrorResponse.builder()
                .code(ErrorCatalog.NO_PUEDE_SER_NULL.getCode())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EvaluacionNoEncontradaException.class)
    public ErrorResponse handleEvaluacionNoEcontrada (EvaluacionNoEncontradaException ex) {
        return ErrorResponse.builder()
                .code(ErrorCatalog.NO_PUEDE_SER_VACIO.getCode())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGeneralException(Exception exception) {
        return ErrorResponse.builder()
                .code(ErrorCatalog.INTERNAL_SERVER_ERROR.getCode())
                .message(ErrorCatalog.INTERNAL_SERVER_ERROR.getMessage())
                .details(List.of(exception.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleErrorResponse(MethodArgumentNotValidException exception) {

        BindingResult result = exception.getBindingResult();

        return ErrorResponse.builder()
                .code(ErrorCatalog.INVALID_CREDENCIALES.getCode())
                .message(ErrorCatalog.INVALID_CREDENCIALES.getMessage())
                .details(result.getFieldErrors()
                        .stream()
                        .map(error -> error.getField() + ": " + error.getDefaultMessage())
                        .toList())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
