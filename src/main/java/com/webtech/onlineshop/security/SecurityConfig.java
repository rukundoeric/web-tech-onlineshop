package com.webtech.onlineshop.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webtech.onlineshop.common.ResponseObject;
import com.webtech.onlineshop.config.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfig jwtConfig;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String[] PUBLIC_MATCHERS = {
            "/",
            "/**/public/**",
            "/login/**",
            "/signin/**",
            "/signup/**",
            "/signout/**",
    };

    private static final String[] ADMIN_MATCHERS = {
            "/**/admin/**"
    };

    private static final String[] USER_MATCHERS = {
            "/**/user/**"
    };

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .antMatchers(ADMIN_MATCHERS).hasAnyAuthority("ADMIN")
                .antMatchers(USER_MATCHERS).hasAnyAuthority("USER", "ADMIN")
                .anyRequest().hasAnyAuthority("USER","ADMIN")
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(
                        (request, response, authException) -> {
                            if (authException != null) {
                                ResponseObject obj = new ResponseObject();
                                Map<String, Object> data = new HashMap<>();
                                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                                if (auth == null) {
                                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                    response.setCharacterEncoding("UTF-8");
                                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                                    data.put("status", response.getStatus());
                                    data.put("message", "Unauthorized");
                                } else {
                                    response.setStatus(HttpStatus.FORBIDDEN.value());
                                    response.setCharacterEncoding("UTF-8");
                                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                                    data.put("status", response.getStatus());
                                    data.put("message", "Access Denied for : " + auth.getAuthorities().toString());
                                }
                                obj.setData(data);
                                obj.setStatus(false);
                                obj.setMessage("Access Denied");

                                response.getOutputStream().println(objectMapper.writeValueAsString(obj));
                            }
                        }
                    )
                .and()
                    .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(corsFilter(), ChannelProcessingFilter.class); // Add this line
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


}
