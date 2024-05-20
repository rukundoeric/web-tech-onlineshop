/*
 * Copyright (c) 2018 - 2019. Irembo
 *
 * All rights reserved.
 */

package com.webtech.onlineshop.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JwtConfig {

    @Value("${security.jwt.header}")
    private String header;


    @Value("${security.jwt.prefix}")
    private String prefix;


    @Value("${security.jwt.expiration}")
    private long expiration;


    @Value("${security.jwt.secret}")
    private String secret;
}
