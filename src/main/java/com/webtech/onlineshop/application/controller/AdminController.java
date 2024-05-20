package com.webtech.onlineshop.application.controller;

import com.webtech.onlineshop.application.dto.UserDto;
import com.webtech.onlineshop.application.model.Order;
import com.webtech.onlineshop.application.model.User;
import com.webtech.onlineshop.application.service.OrderService;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public AdminController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseObject> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(new ResponseObject(users), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseObject> getUserById(@PathVariable UUID id) {
        Optional<UserDto> userOptional = userService.getUserById(id);
        return userOptional.map(user -> new ResponseEntity<>(new ResponseObject(user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable UUID id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(new ResponseObject("Deleted successfully"), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/orders")
    public ResponseEntity<ResponseObject> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(new ResponseObject(orders), HttpStatus.OK);
    }

    @PutMapping("/orders/reject/{id}")
    public ResponseEntity<ResponseObject> rejectOrders(@PathVariable UUID id) {
        Optional<Order> orderOptional = orderService.getOrderById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus("REJECTED");
            orderService.updateOrder(id, order);
        }
        return new ResponseEntity<>(new ResponseObject(orderOptional.get()), HttpStatus.OK);
    }

    @PutMapping("/orders/approve/{id}")
    public ResponseEntity<ResponseObject> approveOrders(@PathVariable UUID id) {
        Optional<Order> orderOptional = orderService.getOrderById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus("APPROVED");
            orderService.updateOrder(id, order);
        }
        return new ResponseEntity<>(new ResponseObject(orderOptional.get()), HttpStatus.OK);
    }

}
