package com.webtech.onlineshop.application.controller;

import com.webtech.onlineshop.application.model.Order;
import com.webtech.onlineshop.application.model.User;
import com.webtech.onlineshop.application.service.OrderProductService;
import com.webtech.onlineshop.application.service.OrderService;
import com.webtech.onlineshop.application.service.UserService;
import com.webtech.onlineshop.common.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping
public class OrderAndCartController {
    private final OrderService orderService;
    private final UserService userService;
    private final OrderProductService orderProductService;

    @Autowired
    public OrderAndCartController(OrderService orderService, UserService userService, OrderProductService orderProductService) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderProductService = orderProductService;
    }



    @GetMapping("/user/orders/{userId}")
    public ResponseEntity<ResponseObject> getOrderByUser(@PathVariable UUID userId) {
        Optional<User> user = userService.findUserById(userId);
        if (user.isPresent()) {
            List<Order> orders = orderService.getOrderByUser(user.get());
            return new ResponseEntity<>(new ResponseObject(orders), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseObject(Collections.emptyList()), HttpStatus.OK);
        }
    }

    @PostMapping("users/orders")
    public ResponseEntity<ResponseObject> createOrder(@RequestBody Order order) {
        Order saveOrder = orderService.saveOrder(order);
        return new ResponseEntity<>(new ResponseObject(saveOrder), HttpStatus.CREATED);
    }

    @PutMapping("users/orders/cancel/{id}")
    public ResponseEntity<ResponseObject> cancelOrders(@PathVariable UUID id) {
        Optional<Order> orderOptional = orderService.getOrderById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus("CANCELED");
            orderService.updateOrder(id, order);
        }
        return new ResponseEntity<>(new ResponseObject(orderOptional.get()), HttpStatus.OK);
    }

    @PutMapping("users/orders/uncancel/{id}")
    public ResponseEntity<ResponseObject> unCancelOrders(@PathVariable UUID id) {
        Optional<Order> orderOptional = orderService.getOrderById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus("PENDING");
            orderService.updateOrder(id, order);
        }
        return new ResponseEntity<>(new ResponseObject(orderOptional.get()), HttpStatus.OK);
    }
}
