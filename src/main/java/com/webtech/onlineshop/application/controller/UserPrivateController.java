package com.webtech.onlineshop.application.controller;

import com.webtech.onlineshop.application.dto.UserDto;
import com.webtech.onlineshop.application.model.User;
import com.webtech.onlineshop.application.service.UserService;
import com.webtech.onlineshop.common.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserPrivateController {
    private final UserService userService;

    @Autowired
    public UserPrivateController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(new ResponseObject(users), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getUserById(@PathVariable UUID id) {
        Optional<UserDto> userOptional = userService.getUserById(id);
        return userOptional.map(user -> new ResponseEntity<>(new ResponseObject(user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateAccount(@PathVariable UUID id, @RequestBody User updatedUser) {
        UserDto user = userService.updateUser(id, updatedUser);
        if (user != null) {
            return new ResponseEntity<>(new ResponseObject(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
