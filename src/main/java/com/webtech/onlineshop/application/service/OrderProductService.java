package com.webtech.onlineshop.application.service;

import com.webtech.onlineshop.application.model.Order;
import com.webtech.onlineshop.application.model.OrderProduct;
import com.webtech.onlineshop.application.model.Product;
import com.webtech.onlineshop.application.repository.IOrderProductRepository;
import com.webtech.onlineshop.application.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderProductService {

    @Autowired
    IOrderProductRepository orderProductRepository;

    public List<OrderProduct> getAllOrderProducts() {
        try {
            return orderProductRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    public List<OrderProduct> getAllOrderProductsByOrder(Order order) {
        try {
            return orderProductRepository.findByOrder(order);
        } catch (Exception e) {
            throw e;
        }
    }

    public Optional<OrderProduct> getOrderProductById(UUID id) {
        try {
            return orderProductRepository.findById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    public OrderProduct saveOrderProduct(OrderProduct orderProduct) {
        try {
            return orderProductRepository.save(orderProduct);
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteOrderProduct(UUID id) {
        try {
            orderProductRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    public OrderProduct updateOrderProduct(UUID id, OrderProduct updatedOrderProduct) {
        try {
            Optional<OrderProduct> existingProductOptional = orderProductRepository.findById(id);
            if (existingProductOptional.isPresent()) {
                OrderProduct existingOrderProduct = existingProductOptional.get();
                existingOrderProduct.setProduct(updatedOrderProduct.getProduct());
                existingOrderProduct.setOrder(updatedOrderProduct.getOrder());
                existingOrderProduct.setQuantity(updatedOrderProduct.getQuantity());
                return orderProductRepository.save(existingOrderProduct);
            } else {
                // Handle the case where the product with the given ID does not exist
                return null; // Or throw an exception
            }
        } catch (Exception e) {
            throw e;
        }

    }

}
