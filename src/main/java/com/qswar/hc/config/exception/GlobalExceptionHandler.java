package com.qswar.hc.config.exception;


import com.qswar.hc.constants.Constant;
import com.qswar.hc.exception.ResourceNotFoundException;
import com.qswar.hc.pojos.responses.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                Constant.FAIL,
                ex.getMessage(),
                System.currentTimeMillis(),
                request.getDescription(false).replace("uri=", "") // Extracts the path
        );

        // Return the formatted response with the appropriate HTTP status code
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                "500 INTERNAL SERVER ERROR",
                "An unexpected error occurred. Please try again later.", // General message for security
                System.currentTimeMillis(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
