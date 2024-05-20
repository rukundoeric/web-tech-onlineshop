package com.webtech.onlineshop.utils;

import org.springframework.security.core.Authentication;

public interface IJwtUtil {
    public String generateToken(Authentication authentication);
}
