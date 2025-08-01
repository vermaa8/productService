package com.scaler.capstone.productservice.ProductService.repositories;

import com.scaler.capstone.productservice.ProductService.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
