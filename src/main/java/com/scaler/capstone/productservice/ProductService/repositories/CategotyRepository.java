package com.scaler.capstone.productservice.ProductService.repositories;

import com.scaler.capstone.productservice.ProductService.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategotyRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
