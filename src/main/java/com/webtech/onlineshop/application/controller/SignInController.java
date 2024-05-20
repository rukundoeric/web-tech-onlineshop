package com.webtech.onlineshop.application.controller;

import com.webtech.onlineshop.application.dto.UserDto;
import com.webtech.onlineshop.application.model.User;
import com.webtech.onlineshop.application.service.UserService;
import com.webtech.onlineshop.common.ResponseObject;
import com.webtech.onlineshop.security.AuthenticationResponse;
import com.webtech.onlineshop.utils.IJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping
public class SignInController {
    private final UserService userService;

    /**
     * The Authentication manager.
     */
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private IJwtUtil jwtUtil;

    AuthenticationResponse authResponse = new AuthenticationResponse();

    @Autowired
    public SignInController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseObject signIn(@RequestBody User request, HttpServletRequest req, HttpServletResponse res) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String token = request.getEmail().concat(" is not authenticated");
            if (authentication != null) {
                UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                );
                authentication = authenticationManager.authenticate(authReq);
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authentication);
            }
            token = jwtUtil.generateToken(authentication);
            UserDto user = new UserDto();
            Optional<UserDto> userOptional = userService.getUserByEmail(request.getEmail());
//            Optional<User> sessionUserOptional = userService.findUserByEmail(request.getEmail());

            if (userOptional.isPresent()) {
                user = userOptional.get();
            }
            return authResponse.handleAuthenticationSuccess(token, user, req);
        } catch (AuthenticationException ex) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return authResponse.handleAuthenticationFailure(ex, req);
        } catch (Exception ex) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new ResponseObject(ex);
        }
    }

    @PostMapping("/signout")
    public ResponseObject signOut(@RequestBody User request, HttpServletRequest req, HttpServletResponse res) {
        try {
            Optional<User> sessionUserOptional = userService.findUserByEmail(request.getEmail());
            if (sessionUserOptional.isPresent()) {
                User sessionUser = sessionUserOptional.get();
//                sessionUser.setSessionToken(null);
                userService.updateUser(sessionUser.getId(), sessionUser);
            }
            return authResponse.handleLogoutSuccess(req);
        } catch (Exception ex) {
            return new ResponseObject(ex);
        }
    }
}
