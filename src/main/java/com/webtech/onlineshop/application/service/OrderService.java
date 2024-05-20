package com.webtech.onlineshop.application.service;

import com.webtech.onlineshop.application.model.Order;
import com.webtech.onlineshop.application.model.OrderProduct;
import com.webtech.onlineshop.application.model.User;
import com.webtech.onlineshop.application.repository.IOrderProductRepository;
import com.webtech.onlineshop.application.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    IOrderRepository orderRepository;

    @Autowired
    IOrderProductRepository orderProductRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public List<Order>  getAllOrders() {
        try {
            return orderRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public Optional<Order> getOrderById(UUID id) {
        try {
            return orderRepository.findById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public List<Order> getOrderByUser(User user) {
        try {
            return orderRepository.findByUser(user);
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public Order saveOrder(Order order) {
        try {
            List<OrderProduct> products = order.getOrderProducts();
            order.setOrderProducts(null);
            Order order1 = orderRepository.save(order);

            List<OrderProduct> orderProducts = products.stream()
                    .peek(orderProduct -> orderProduct.setOrder(order1))
                    .collect(Collectors.toList());
            orderProductRepository.saveAll(orderProducts);

            return order;
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteProduct(UUID id) {
        try {
            orderRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateOrder(UUID id, Order order) {
        try {
            Optional<Order> existingOrderOptional = orderRepository.findById(id);
            if (existingOrderOptional.isPresent()) {
                Order existingOrder = existingOrderOptional.get();
                existingOrder.setUser(order.getUser());
                existingOrder.setTotalPrice(order.getTotalPrice());
                existingOrder.setStatus(order.getStatus());
                orderRepository.save(existingOrder);
            } else {
                // Handle the case where the product with the given ID does not exist
            }
        } catch (Exception e) {
            throw e;
        }

    }

}
