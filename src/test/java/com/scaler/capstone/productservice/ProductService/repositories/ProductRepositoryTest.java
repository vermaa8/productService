package com.scaler.capstone.productservice.ProductService.repositories;

import com.scaler.capstone.productservice.ProductService.models.Category;
import com.scaler.capstone.productservice.ProductService.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategotyRepository categoryRepository;

    private Category electronicsCategory;
    private Category furnitureCategory;
    private Product iphoneProduct;
    private Product macbookProduct;
    private Product chairProduct;

    @BeforeEach
    void setUp() {
        // Create categories
        electronicsCategory = new Category("Electronics", "Electronic devices and gadgets");
        furnitureCategory = new Category("Furniture", "Home and office furniture");
        
        electronicsCategory = categoryRepository.save(electronicsCategory);
        furnitureCategory = categoryRepository.save(furnitureCategory);

        // Create products
        iphoneProduct = new Product("iPhone 15", "Latest iPhone model", 999.99, "iphone15.jpg", electronicsCategory);
        macbookProduct = new Product("MacBook Pro", "Professional laptop", 1999.99, "macbook.jpg", electronicsCategory);
        chairProduct = new Product("Gaming Chair", "Ergonomic gaming chair", 299.99, "chair.jpg", furnitureCategory);

        iphoneProduct = productRepository.save(iphoneProduct);
        macbookProduct = productRepository.save(macbookProduct);
        chairProduct = productRepository.save(chairProduct);
    }

    @Test
    void testSaveProduct() {
        // Given
        Product newProduct = new Product("iPad Pro", "Professional tablet", 1099.99, "ipad.jpg", electronicsCategory);

        // When
        Product savedProduct = productRepository.save(newProduct);

        // Then
        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());
        assertEquals("iPad Pro", savedProduct.getTitle());
        assertEquals("Professional tablet", savedProduct.getDescription());
        assertEquals(1099.99, savedProduct.getPrice());
        assertEquals("ipad.jpg", savedProduct.getImageUrl());
        assertEquals(electronicsCategory.getId(), savedProduct.getCategory().getId());
    }

    @Test
    void testFindById_Success() {
        // When
        Optional<Product> foundProduct = productRepository.findById(iphoneProduct.getId());

        // Then
        assertTrue(foundProduct.isPresent());
        assertEquals(iphoneProduct.getId(), foundProduct.get().getId());
        assertEquals("iPhone 15", foundProduct.get().getTitle());
        assertEquals("Latest iPhone model", foundProduct.get().getDescription());
        assertEquals(999.99, foundProduct.get().getPrice());
        assertEquals("iphone15.jpg", foundProduct.get().getImageUrl());
        assertEquals(electronicsCategory.getId(), foundProduct.get().getCategory().getId());
    }

    @Test
    void testFindById_NotFound() {
        // Given
        Long nonExistentId = 999L;

        // When
        Optional<Product> foundProduct = productRepository.findById(nonExistentId);

        // Then
        assertFalse(foundProduct.isPresent());
    }

    @Test
    void testFindAll() {
        // When
        List<Product> products = productRepository.findAll();

        // Then
        assertNotNull(products);
        assertEquals(3, products.size());
        assertTrue(products.stream().anyMatch(p -> p.getTitle().equals("iPhone 15")));
        assertTrue(products.stream().anyMatch(p -> p.getTitle().equals("MacBook Pro")));
        assertTrue(products.stream().anyMatch(p -> p.getTitle().equals("Gaming Chair")));
    }

    @Test
    void testFindByCategory() {
        // When
        List<Product> electronicsProducts = productRepository.findByCategory(electronicsCategory);
        List<Product> furnitureProducts = productRepository.findByCategory(furnitureCategory);

        // Then
        assertNotNull(electronicsProducts);
        assertEquals(2, electronicsProducts.size());
        assertTrue(electronicsProducts.stream().allMatch(p -> p.getCategory().getId().equals(electronicsCategory.getId())));

        assertNotNull(furnitureProducts);
        assertEquals(1, furnitureProducts.size());
        assertTrue(furnitureProducts.stream().allMatch(p -> p.getCategory().getId().equals(furnitureCategory.getId())));
    }

    @Test
    void testFindByCategory_NameEquals() {
        // When
        List<Product> electronicsProducts = productRepository.findByCategory_NameEquals("Electronics");
        List<Product> furnitureProducts = productRepository.findByCategory_NameEquals("Furniture");

        // Then
        assertNotNull(electronicsProducts);
        assertEquals(2, electronicsProducts.size());
        assertTrue(electronicsProducts.stream().allMatch(p -> p.getCategory().getName().equals("Electronics")));

        assertNotNull(furnitureProducts);
        assertEquals(1, furnitureProducts.size());
        assertTrue(furnitureProducts.stream().allMatch(p -> p.getCategory().getName().equals("Furniture")));
    }

    @Test
    void testFindByCategory_NameEquals_NonExistentCategory() {
        // When
        List<Product> products = productRepository.findByCategory_NameEquals("NonExistent");

        // Then
        assertNotNull(products);
        assertEquals(0, products.size());
    }

    @Test
    void testUpdateProduct() {
        // Given
        Product productToUpdate = productRepository.findById(iphoneProduct.getId()).get();
        productToUpdate.setTitle("iPhone 15 Pro");
        productToUpdate.setPrice(1199.99);

        // When
        Product updatedProduct = productRepository.save(productToUpdate);

        // Then
        assertNotNull(updatedProduct);
        assertEquals(iphoneProduct.getId(), updatedProduct.getId());
        assertEquals("iPhone 15 Pro", updatedProduct.getTitle());
        assertEquals(1199.99, updatedProduct.getPrice());
        assertEquals("Latest iPhone model", updatedProduct.getDescription()); // Should remain unchanged
        assertEquals("iphone15.jpg", updatedProduct.getImageUrl()); // Should remain unchanged
    }

    @Test
    void testDeleteProduct() {
        // Given
        Long productId = iphoneProduct.getId();

        // When
        productRepository.deleteById(productId);

        // Then
        Optional<Product> deletedProduct = productRepository.findById(productId);
        assertFalse(deletedProduct.isPresent());

        List<Product> remainingProducts = productRepository.findAll();
        assertEquals(2, remainingProducts.size());
        assertFalse(remainingProducts.stream().anyMatch(p -> p.getId().equals(productId)));
    }

    @Test
    void testCount() {
        // When
        long count = productRepository.count();

        // Then
        assertEquals(3, count);
    }

    @Test
    void testExistsById() {
        // When & Then
        assertTrue(productRepository.existsById(iphoneProduct.getId()));
        assertTrue(productRepository.existsById(macbookProduct.getId()));
        assertTrue(productRepository.existsById(chairProduct.getId()));
        assertFalse(productRepository.existsById(999L));
    }
} 