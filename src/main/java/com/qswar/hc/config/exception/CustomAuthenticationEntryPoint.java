package com.qswar.hc.config.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qswar.hc.constants.Constant;
import com.qswar.hc.pojos.responses.exception.ErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        // 1. Set the HTTP Status
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401

        // 2. Set the Content Type to JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // 3. Create the formatted ErrorResponse object
        ErrorResponse errorResponse = new ErrorResponse(
                Constant.FAIL,
                "Authentication required. Please provide valid credentials or token.",
                System.currentTimeMillis(),
                request.getRequestURI()
        );

        // 4. Write the ErrorResponse object to the response output stream as JSON
        // Note: We use the Response's writer/output stream, NOT a ResponseEntity.
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}