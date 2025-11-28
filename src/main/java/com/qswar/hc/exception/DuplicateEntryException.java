package com.qswar.hc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception class to handle scenarios where an attempt is made
 * to create a resource (e.g., an AllowlistUser) that already exists.
 * It automatically translates to an HTTP 409 Conflict response code.
 */
@ResponseStatus(HttpStatus.CONFLICT) // Sets the HTTP status code to 409
public class DuplicateEntryException extends RuntimeException {

    // Unique serial version ID
    private static final long serialVersionUID = 1L;

    /**
     * Constructor that accepts a custom message.
     * @param message The detail message of the exception.
     */
    public DuplicateEntryException(String message) {
        // Calls the constructor of the parent class (RuntimeException)
        super(message);
    }
}