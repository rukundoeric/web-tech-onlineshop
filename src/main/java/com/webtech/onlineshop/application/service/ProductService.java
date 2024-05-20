package com.webtech.onlineshop.application.service;

import com.webtech.onlineshop.application.model.Product;
import com.webtech.onlineshop.application.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    IProductRepository productRepository;

    public List<Product> getAllProducts() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    public Optional<Product> getProductById(UUID id) {
        try {
            return productRepository.findById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    public Product saveProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteProduct(UUID id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    public Product updateProduct(UUID id, Product updatedProduct) {
        try {
            Optional<Product> existingProductOptional = productRepository.findById(id);
            if (existingProductOptional.isPresent()) {
                Product existingProduct = existingProductOptional.get();
                existingProduct.setName(updatedProduct.getName());
                existingProduct.setDescription(updatedProduct.getDescription());
                existingProduct.setPrice(updatedProduct.getPrice());
                existingProduct.setImage(updatedProduct.getImage());
                return productRepository.save(existingProduct);
            } else {
                // Handle the case where the product with the given ID does not exist
                return null; // Or throw an exception
            }
        } catch (Exception e) {
            throw e;
        }

    }

}
