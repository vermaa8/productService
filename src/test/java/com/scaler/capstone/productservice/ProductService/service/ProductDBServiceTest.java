package com.scaler.capstone.productservice.ProductService.service;

import com.scaler.capstone.productservice.ProductService.exceptions.ProductNotFoundException;
import com.scaler.capstone.productservice.ProductService.models.Category;
import com.scaler.capstone.productservice.ProductService.models.Product;
import com.scaler.capstone.productservice.ProductService.repositories.CategotyRepository;
import com.scaler.capstone.productservice.ProductService.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProductDBServiceTest {

    @Autowired
    private ProductDBService productDBService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategotyRepository categoryRepository;

    private Category testCategory;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        // Create test category
        testCategory = new Category("Electronics", "Electronic devices and gadgets");
        testCategory = categoryRepository.save(testCategory);

        // Create test product
        testProduct = new Product("iPhone 15", "Latest iPhone model", 999.99, "iphone15.jpg", testCategory);
        testProduct = productRepository.save(testProduct);
    }

    @Test
    void testGetProductById_Success() throws ProductNotFoundException {
        // When
        Product foundProduct = productDBService.getProductById(testProduct.getId());

        // Then
        assertNotNull(foundProduct);
        assertEquals(testProduct.getId(), foundProduct.getId());
        assertEquals(testProduct.getTitle(), foundProduct.getTitle());
        assertEquals(testProduct.getDescription(), foundProduct.getDescription());
        assertEquals(testProduct.getPrice(), foundProduct.getPrice());
        assertEquals(testProduct.getImageUrl(), foundProduct.getImageUrl());
        assertEquals(testProduct.getCategory().getId(), foundProduct.getCategory().getId());
    }

    @Test
    void testGetProductById_NotFound() {
        // Given
        Long nonExistentId = 999L;

        // When & Then
        assertThrows(ProductNotFoundException.class, () -> {
            productDBService.getProductById(nonExistentId);
        });
    }

    @Test
    void testGetAllProducts_Success() {
        // Given
        Product secondProduct = new Product("MacBook Pro", "Professional laptop", 1999.99, "macbook.jpg", testCategory);
        productRepository.save(secondProduct);

        // When
        List<Product> products = productDBService.getAllProducts();

        // Then
        assertNotNull(products);
        assertTrue(products.size() >= 2);
        assertTrue(products.stream().anyMatch(p -> p.getTitle().equals("iPhone 15")));
        assertTrue(products.stream().anyMatch(p -> p.getTitle().equals("MacBook Pro")));
    }

    @Test
    void testCreateProduct_WithExistingCategory() {
        // Given
        String title = "iPad Pro";
        String description = "Professional tablet";
        Double price = 1099.99;
        String imageUrl = "ipad.jpg";
        String categoryName = "Electronics"; // Existing category

        // When
        Product createdProduct = productDBService.createProduct(title, description, price, imageUrl, categoryName);

        // Then
        assertNotNull(createdProduct);
        assertNotNull(createdProduct.getId());
        assertEquals(title, createdProduct.getTitle());
        assertEquals(description, createdProduct.getDescription());
        assertEquals(price, createdProduct.getPrice());
        assertEquals(imageUrl, createdProduct.getImageUrl());
        assertEquals(categoryName, createdProduct.getCategory().getName());
        assertEquals(testCategory.getId(), createdProduct.getCategory().getId());
    }

    @Test
    void testCreateProduct_WithNewCategory() {
        // Given
        String title = "Gaming Chair";
        String description = "Ergonomic gaming chair";
        Double price = 299.99;
        String imageUrl = "chair.jpg";
        String categoryName = "Furniture"; // New category

        // When
        Product createdProduct = productDBService.createProduct(title, description, price, imageUrl, categoryName);

        // Then
        assertNotNull(createdProduct);
        assertNotNull(createdProduct.getId());
        assertEquals(title, createdProduct.getTitle());
        assertEquals(description, createdProduct.getDescription());
        assertEquals(price, createdProduct.getPrice());
        assertEquals(imageUrl, createdProduct.getImageUrl());
        assertEquals(categoryName, createdProduct.getCategory().getName());
        
        // Verify category was created
        Optional<Category> savedCategory = categoryRepository.findByName(categoryName);
        assertTrue(savedCategory.isPresent());
        assertEquals(categoryName, savedCategory.get().getName());
    }

    @Test
    void testPartialUpdate_Success() throws ProductNotFoundException {
        // Given
        Product updateProduct = new Product();
        updateProduct.setTitle("iPhone 15 Pro");
        updateProduct.setPrice(1199.99);

        // When
        Product updatedProduct = productDBService.partialUpdate(testProduct.getId(), updateProduct);

        // Then
        assertNotNull(updatedProduct);
        assertEquals("iPhone 15 Pro", updatedProduct.getTitle());
        assertEquals(1199.99, updatedProduct.getPrice());
        assertEquals(testProduct.getDescription(), updatedProduct.getDescription()); // Should remain unchanged
        assertEquals(testProduct.getImageUrl(), updatedProduct.getImageUrl()); // Should remain unchanged
        assertEquals(testProduct.getCategory().getId(), updatedProduct.getCategory().getId()); // Should remain unchanged
    }

    @Test
    void testPartialUpdate_WithNewCategory() throws ProductNotFoundException {
        // Given
        Product updateProduct = new Product();
        updateProduct.setTitle("iPhone 15 Pro");
        Category newCategory = new Category("Smartphones", "Mobile phones");
        updateProduct.setCategory(newCategory);

        // When
        Product updatedProduct = productDBService.partialUpdate(testProduct.getId(), updateProduct);

        // Then
        assertNotNull(updatedProduct);
        assertEquals("iPhone 15 Pro", updatedProduct.getTitle());
        assertEquals("Smartphones", updatedProduct.getCategory().getName());
        
        // Verify new category was created
        Optional<Category> savedCategory = categoryRepository.findByName("Smartphones");
        assertTrue(savedCategory.isPresent());
    }

    @Test
    void testPartialUpdate_ProductNotFound() {
        // Given
        Long nonExistentId = 999L;
        Product updateProduct = new Product();
        updateProduct.setTitle("Updated Title");

        // When & Then
        assertThrows(ProductNotFoundException.class, () -> {
            productDBService.partialUpdate(nonExistentId, updateProduct);
        });
    }

    @Test
    void testPartialUpdate_WithNullValues() throws ProductNotFoundException {
        // Given
        Product updateProduct = new Product();
        updateProduct.setTitle("Updated Title");
        // Other fields are null

        // When
        Product updatedProduct = productDBService.partialUpdate(testProduct.getId(), updateProduct);

        // Then
        assertNotNull(updatedProduct);
        assertEquals("Updated Title", updatedProduct.getTitle());
        assertEquals(testProduct.getDescription(), updatedProduct.getDescription()); // Should remain unchanged
        assertEquals(testProduct.getPrice(), updatedProduct.getPrice()); // Should remain unchanged
        assertEquals(testProduct.getImageUrl(), updatedProduct.getImageUrl()); // Should remain unchanged
        assertEquals(testProduct.getCategory().getId(), updatedProduct.getCategory().getId()); // Should remain unchanged
    }

    @Test
    void testPartialUpdate_WithCategoryName() throws ProductNotFoundException {
        // Given
        Product updateProduct = new Product();
        updateProduct.setTitle("iPhone 15 Pro");
        String newCategoryName = "Smartphones";

        // When
        Product updatedProduct = productDBService.partialUpdate(testProduct.getId(), updateProduct, newCategoryName);

        // Then
        assertNotNull(updatedProduct);
        assertEquals("iPhone 15 Pro", updatedProduct.getTitle());
        assertEquals("Smartphones", updatedProduct.getCategory().getName());
        
        // Verify new category was created
        Optional<Category> savedCategory = categoryRepository.findByName("Smartphones");
        assertTrue(savedCategory.isPresent());
    }

    @Test
    void testPartialUpdate_WithCategoryName_ProductNotFound() {
        // Given
        Long nonExistentId = 999L;
        Product updateProduct = new Product();
        updateProduct.setTitle("Updated Title");
        String categoryName = "Electronics";

        // When & Then
        assertThrows(ProductNotFoundException.class, () -> {
            productDBService.partialUpdate(nonExistentId, updateProduct, categoryName);
        });
    }
} 