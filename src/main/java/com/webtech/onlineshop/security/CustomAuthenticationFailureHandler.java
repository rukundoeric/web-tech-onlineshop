package com.webtech.onlineshop.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webtech.onlineshop.common.ResponseObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    AuthenticationResponse authResponse = new AuthenticationResponse();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        ResponseObject object = authResponse.handleAuthenticationFailure(exception, request);
        response.getOutputStream().println(objectMapper.writeValueAsString(object));
    }
}
