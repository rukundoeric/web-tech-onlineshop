package com.webtech.onlineshop.application.repository;

import com.webtech.onlineshop.application.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProductRepository extends JpaRepository<Product, UUID> {
    // Will add custom query methods here if needed
}
