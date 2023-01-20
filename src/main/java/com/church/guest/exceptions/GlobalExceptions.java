package com.church.guest.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.church.guest.web.dto.Error;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity error(RuntimeException runtimeException){

        return ResponseEntity.badRequest().body(Error.builder()
                .message(runtimeException.getMessage())
                .traceId(UUID.randomUUID().toString())
                .build()
        );
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity error(EntityNotFoundException entityNotFoundException){

        return ResponseEntity.badRequest().body(Error.builder()
                .message(entityNotFoundException.getMessage())
                .traceId(UUID.randomUUID().toString())
                .build()
        );
    }
}
