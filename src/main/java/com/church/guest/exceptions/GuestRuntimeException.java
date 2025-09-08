package com.church.guest.exceptions;

import org.springframework.http.HttpStatus;

public class GuestRuntimeException extends RuntimeException {

    private final HttpStatus status;

    public GuestRuntimeException( String message, HttpStatus status ) {
        super( message );
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
