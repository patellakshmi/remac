package com.qswar.hc.config.exception;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.qswar.hc.constants.Constant;
import com.qswar.hc.pojos.responses.exception.ErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // Log the exception for debugging (optional)
        System.err.println("Access denied: " + accessDeniedException.getMessage());

        // 1. Set the response status to 403 Forbidden
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403

        // 2. Set the content type to application/json
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // 3. Create the formatted ErrorResponse object
        // NOTE: Use a clear message indicating a lack of permission/role
        ErrorResponse errorResponse = new ErrorResponse(
                Constant.FAIL,
                "You do not have permission to access this resource.",
                System.currentTimeMillis(),
                request.getRequestURI()
        );

        // 4. Write the JSON response body
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}