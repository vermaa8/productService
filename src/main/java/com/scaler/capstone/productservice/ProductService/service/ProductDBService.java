package com.scaler.capstone.productservice.ProductService.service;

import com.scaler.capstone.productservice.ProductService.exceptions.ProductNotFoundException;
import com.scaler.capstone.productservice.ProductService.models.Category;
import com.scaler.capstone.productservice.ProductService.models.Product;
import com.scaler.capstone.productservice.ProductService.repositories.CategotyRepository;
import com.scaler.capstone.productservice.ProductService.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("productDBService")
public class ProductDBService implements ProductService{

    private ProductRepository productRepository;
    private CategotyRepository categotyRepository;

    public ProductDBService(ProductRepository productRepository, CategotyRepository categotyRepository) {
        this.productRepository = productRepository;
         this.categotyRepository = categotyRepository;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String title, String description, Double price, String imageUrl, String categoryName) {
        Product product = new Product(title, description,price, imageUrl, getCategoryFromDB(categoryName));
        return productRepository.save(product);
    }

    @Override
    public Product partialUpdate(Long id, Product product) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException("{roduct that y6ou want to update is not found");
        }
        Product product1ToUpdate = productOptional.get();
        if (product.getTitle() != null) {
            product1ToUpdate.setTitle(product.getTitle());
        }
        if (product.getDescription() != null) {
            product1ToUpdate.setDescription(product.getDescription());
        }
        if (product.getPrice() != null) {
            product1ToUpdate.setPrice(product.getPrice());
        }
        if (product.getImageUrl() != null) {
            product1ToUpdate.setImageUrl(product.getImageUrl());
        }
        if (product.getCategory() != null) {
            product1ToUpdate.setCategory(getCategoryFromDB(product.getCategory().getName()));
        }
        return productRepository.save(product1ToUpdate);
    }
    private Category getCategoryFromDB(String categoryName) {
        Optional<Category> optionalcategory = categotyRepository.findByName(categoryName);
        if (optionalcategory.isEmpty()) {
            Category category = new Category();
            category.setName(categoryName);
            categotyRepository.save(category);
            return category;
        }
        return optionalcategory.get();
    }
}
