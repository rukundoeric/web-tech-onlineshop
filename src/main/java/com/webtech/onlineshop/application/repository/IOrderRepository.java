package com.webtech.onlineshop.application.repository;

import com.webtech.onlineshop.application.model.Order;
import com.webtech.onlineshop.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IOrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUser(User user);
}
