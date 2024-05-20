package com.webtech.onlineshop.utils;

import com.webtech.onlineshop.common.RequestException;
import com.webtech.onlineshop.config.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil implements IJwtUtil {

    /**
     * The Jwt config.
     */
    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public String generateToken(Authentication authentication) {
        try {
            String secretString = jwtConfig.getSecret();
            long expiration = jwtConfig.getExpiration();
            byte[] secret = secretString.getBytes(StandardCharsets.UTF_8);
            SecretKey key = Keys.hmacShaKeyFor(secret);
            long now = System.currentTimeMillis();
            Date expiryDate = new Date(now + expiration);

            String token = Jwts.builder()
                    .setSubject(authentication.getName())
                    .claim("authorities", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .claim("username", authentication.getName())
                    .setIssuedAt(new Date(now))
                    .setExpiration(expiryDate)
                    .signWith(key)
                    .compact();
            return jwtConfig.getPrefix() + " " + token;
        } catch (IllegalArgumentException e) {
            throw new RequestException(e.getMessage());
        } catch (Exception e) {
            throw new RequestException(e.getMessage());
        }
    }
}
