package com.qswar.hc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    // Unique serial version ID for serialization
    private static final long serialVersionUID = 1L;

    /**
     * Constructor that accepts a custom message.
     * @param message The detail message of the exception.
     */
    public ResourceNotFoundException(String message) {
        // Calls the constructor of the parent class (RuntimeException)
        super(message);
    }

}
