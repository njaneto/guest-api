package com.church.guest.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler( MethodArgumentNotValidException.class )
    public ResponseEntity< Map< String, Object > > handleValidationException( MethodArgumentNotValidException ex ) {
        List< ValidationError > validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map( error -> new ValidationError( error.getField(), error.getDefaultMessage() ) )
                .toList();

        Map< String, Object > body = new LinkedHashMap<>();
        body.put( "message", "Erro de validação" );
        body.put( "traceId", TraceContext.getTraceId() );
        body.put( "errors", validationErrors );

        return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( body );
    }

    @ExceptionHandler( ConstraintViolationException.class )
    public ResponseEntity< Map< String, Object > > handleConstraintViolation( ConstraintViolationException ex ) {
        List< ValidationError > validationErrors = ex.getConstraintViolations()
                .stream()
                .map( violation -> new ValidationError(
                        violation.getPropertyPath().toString(),
                        violation.getMessage() ) )
                .toList();

        Map< String, Object > body = new LinkedHashMap<>();
        body.put( "message", "Erro de validação nos parâmetros da requisição" );
        body.put( "traceId", TraceContext.getTraceId() );
        body.put( "errors", validationErrors );

        return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( body );
    }

    @ExceptionHandler( EntityNotFoundException.class )
    public ResponseEntity< Error > handleEntityNotFoundException( EntityNotFoundException ex ) {
        return ResponseEntity
                .status( HttpStatus.NOT_FOUND )
                .body( Error.builder()
                        .message( ex.getMessage() )
                        .traceId( TraceContext.getTraceId() )
                        .build() );
    }

    @ExceptionHandler( HttpMessageNotReadableException.class )
    public ResponseEntity< Error > handleHttpMessageNotReadable( HttpMessageNotReadableException ex ) {
        return ResponseEntity
                .status( HttpStatus.BAD_REQUEST )
                .body( Error.builder()
                        .message( "Requisição malformada. Verifique o corpo enviado." )
                        .traceId( TraceContext.getTraceId() )
                        .build() );
    }

    @ExceptionHandler( HttpRequestMethodNotSupportedException.class )
    public ResponseEntity< Error > handleMethodNotAllowed( HttpRequestMethodNotSupportedException ex ) {
        return ResponseEntity
                .status( HttpStatus.METHOD_NOT_ALLOWED )
                .body( Error.builder()
                        .message( "Método HTTP não suportado: " + ex.getMethod() )
                        .traceId( TraceContext.getTraceId() )
                        .build() );
    }

    @ExceptionHandler( RuntimeException.class )
    public ResponseEntity< Error > handleRuntimeException( RuntimeException ex ) {
        return ResponseEntity
                .status( HttpStatus.BAD_REQUEST )
                .body( Error.builder()
                        .message( ex.getMessage() )
                        .traceId( TraceContext.getTraceId() )
                        .build() );
    }

    @ExceptionHandler( Exception.class )
    public ResponseEntity< Error > handleGenericException( Exception ex ) {
        return ResponseEntity
                .status( HttpStatus.INTERNAL_SERVER_ERROR )
                .body( Error.builder()
                        .message( "Erro interno no servidor. Tente novamente mais tarde." )
                        .traceId( TraceContext.getTraceId() )
                        .build() );
    }

    @ExceptionHandler( GuestRuntimeException.class )
    public ResponseEntity< Error > handleGuestRuntime( GuestRuntimeException ex ) {
        return ResponseEntity
                .status( ex.getStatus() )
                .body( Error.builder()
                        .message( ex.getMessage() )
                        .traceId( TraceContext.getTraceId() )
                        .build() );
    }
}
