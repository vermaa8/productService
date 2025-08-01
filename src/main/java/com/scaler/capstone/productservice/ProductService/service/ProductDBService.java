package com.scaler.capstone.productservice.ProductService.service;

import com.scaler.capstone.productservice.ProductService.exceptions.ProductNotFoundException;
import com.scaler.capstone.productservice.ProductService.models.Category;
import com.scaler.capstone.productservice.ProductService.models.Product;
import com.scaler.capstone.productservice.ProductService.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productDBService")
public class ProductDBService implements ProductService{

    private ProductRepository productRepository;

    public ProductDBService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product createProduct(String title, String description, Double price, String imageUrl, String categoryName) {
        Product product = new Product(title, description,price, imageUrl, new Category(categoryName, null));
        return productRepository.save(product);
    }

    @Override
    public Product partialUpdate(Long id, Product product) throws ProductNotFoundException {
        return null;
    }
}
