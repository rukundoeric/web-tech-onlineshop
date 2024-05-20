package com.webtech.onlineshop.application.repository;

import com.webtech.onlineshop.application.model.Order;
import com.webtech.onlineshop.application.model.OrderProduct;
import com.webtech.onlineshop.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IOrderProductRepository extends JpaRepository<OrderProduct, UUID> {
    List<OrderProduct> findByOrder(Order order);
}
