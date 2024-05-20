package com.webtech.onlineshop.application.controller;

import com.webtech.onlineshop.common.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RootController {

    @GetMapping
    public ResponseEntity<ResponseObject> root() {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setMessage("Welcome to Web/Tech Online shop");
        return new ResponseEntity<>(new ResponseObject(responseObject), HttpStatus.OK);
    }
}
