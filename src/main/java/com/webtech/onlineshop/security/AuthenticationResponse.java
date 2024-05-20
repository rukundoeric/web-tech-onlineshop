package com.webtech.onlineshop.security;

import com.webtech.onlineshop.application.dto.UserDto;
import com.webtech.onlineshop.common.ResponseObject;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationResponse {
    public ResponseObject handleAuthenticationFailure(Exception exception, HttpServletRequest req) {
        ResponseObject object = new ResponseObject();
        object.setStatus(false);
        object.setMessage("Invalid username or password");
        object.setDetailedMessage(exception.getMessage());
        return object;
    }

    public ResponseObject handleAuthenticationSuccess(String token, UserDto user, HttpServletRequest req) {
        ResponseObject object = new ResponseObject();
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("profile", user);
        object.setStatus(true);
        object.setData(data);
        object.setMessage("Logged In successfully");
        return object;
    }

    public ResponseObject handleLogoutSuccess( HttpServletRequest req) {
        ResponseObject object = new ResponseObject();
        object.setStatus(true);
        object.setMessage("Logged Out successfully");
        return object;
    }
}
