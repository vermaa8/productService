package com.scaler.capstone.productservice.ProductService.repositories;

import com.scaler.capstone.productservice.ProductService.models.Category;
import com.scaler.capstone.productservice.ProductService.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
    List<Product> findByCategory_NameEquals(String categoryName);
}
