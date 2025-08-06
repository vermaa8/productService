package com.scaler.capstone.productservice.ProductService.dtos;

import com.scaler.capstone.productservice.ProductService.models.Category;
import com.scaler.capstone.productservice.ProductService.models.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FakeStoreProductResponseDTOTest {

    @Test
    void testFrom_WithValidData() {
        // Given
        FakeStoreProductResponseDTO dto = new FakeStoreProductResponseDTO();
        dto.setId(1L);
        dto.setTitle("iPhone 15");
        dto.setDescription("Latest iPhone model");
        dto.setPrice(999.99);
        dto.setImage("iphone15.jpg");
        dto.setCategory("Electronics");

        // When
        Product product = FakeStoreProductResponseDTO.from(dto);

        // Then
        assertNotNull(product);
        assertEquals(dto.getId(), product.getId());
        assertEquals(dto.getTitle(), product.getTitle());
        assertEquals(dto.getDescription(), product.getDescription());
        assertEquals(dto.getPrice(), product.getPrice());
        assertEquals(dto.getImage(), product.getImageUrl());
        
        // Verify category
        assertNotNull(product.getCategory());
        assertEquals(dto.getCategory(), product.getCategory().getName());
        assertNull(product.getCategory().getDescription());
    }

    @Test
    void testFrom_WithNullValues() {
        // Given
        FakeStoreProductResponseDTO dto = new FakeStoreProductResponseDTO();
        dto.setId(1L);
        dto.setTitle(null);
        dto.setDescription(null);
        dto.setPrice(null);
        dto.setImage(null);
        dto.setCategory(null);

        // When
        Product product = FakeStoreProductResponseDTO.from(dto);

        // Then
        assertNotNull(product);
        assertEquals(dto.getId(), product.getId());
        assertNull(product.getTitle());
        assertNull(product.getDescription());
        assertNull(product.getPrice());
        assertNull(product.getImageUrl());
        
        // Verify category
        assertNotNull(product.getCategory());
        assertNull(product.getCategory().getName());
        assertNull(product.getCategory().getDescription());
    }

    @Test
    void testToProduct_WithValidData() {
        // Given
        FakeStoreProductResponseDTO dto = new FakeStoreProductResponseDTO();
        dto.setId(1L);
        dto.setTitle("MacBook Pro");
        dto.setDescription("Professional laptop");
        dto.setPrice(1999.99);
        dto.setImage("macbook.jpg");
        dto.setCategory("Electronics");

        // When
        Product product = dto.toProduct();

        // Then
        assertNotNull(product);
        assertEquals(dto.getId(), product.getId());
        assertEquals(dto.getTitle(), product.getTitle());
        assertEquals(dto.getDescription(), product.getDescription());
        assertEquals(dto.getPrice(), product.getPrice());
        assertEquals(dto.getImage(), product.getImageUrl());
        
        // Verify category
        assertNotNull(product.getCategory());
        assertEquals(dto.getCategory(), product.getCategory().getName());
        assertNull(product.getCategory().getDescription());
    }

    @Test
    void testToProduct_WithNullValues() {
        // Given
        FakeStoreProductResponseDTO dto = new FakeStoreProductResponseDTO();
        dto.setId(1L);
        dto.setTitle(null);
        dto.setDescription(null);
        dto.setPrice(null);
        dto.setImage(null);
        dto.setCategory(null);

        // When
        Product product = dto.toProduct();

        // Then
        assertNotNull(product);
        assertEquals(dto.getId(), product.getId());
        assertNull(product.getTitle());
        assertNull(product.getDescription());
        assertNull(product.getPrice());
        assertNull(product.getImageUrl());
        
        // Verify category
        assertNotNull(product.getCategory());
        assertNull(product.getCategory().getName());
        assertNull(product.getCategory().getDescription());
    }

    @Test
    void testToProduct_WithZeroPrice() {
        // Given
        FakeStoreProductResponseDTO dto = new FakeStoreProductResponseDTO();
        dto.setId(1L);
        dto.setTitle("Free Product");
        dto.setDescription("Free item");
        dto.setPrice(0.0);
        dto.setImage("free.jpg");
        dto.setCategory("Free");

        // When
        Product product = dto.toProduct();

        // Then
        assertNotNull(product);
        assertEquals(dto.getId(), product.getId());
        assertEquals(dto.getTitle(), product.getTitle());
        assertEquals(dto.getDescription(), product.getDescription());
        assertEquals(dto.getPrice(), product.getPrice());
        assertEquals(dto.getImage(), product.getImageUrl());
        assertEquals(dto.getCategory(), product.getCategory().getName());
    }

    @Test
    void testToProduct_WithNegativePrice() {
        // Given
        FakeStoreProductResponseDTO dto = new FakeStoreProductResponseDTO();
        dto.setId(1L);
        dto.setTitle("Discounted Product");
        dto.setDescription("Discounted item");
        dto.setPrice(-10.0);
        dto.setImage("discount.jpg");
        dto.setCategory("Discount");

        // When
        Product product = dto.toProduct();

        // Then
        assertNotNull(product);
        assertEquals(dto.getId(), product.getId());
        assertEquals(dto.getTitle(), product.getTitle());
        assertEquals(dto.getDescription(), product.getDescription());
        assertEquals(dto.getPrice(), product.getPrice());
        assertEquals(dto.getImage(), product.getImageUrl());
        assertEquals(dto.getCategory(), product.getCategory().getName());
    }

    @Test
    void testFrom_WithEmptyStrings() {
        // Given
        FakeStoreProductResponseDTO dto = new FakeStoreProductResponseDTO();
        dto.setId(1L);
        dto.setTitle("");
        dto.setDescription("");
        dto.setPrice(0.0);
        dto.setImage("");
        dto.setCategory("");

        // When
        Product product = FakeStoreProductResponseDTO.from(dto);

        // Then
        assertNotNull(product);
        assertEquals(dto.getId(), product.getId());
        assertEquals("", product.getTitle());
        assertEquals("", product.getDescription());
        assertEquals(0.0, product.getPrice());
        assertEquals("", product.getImageUrl());
        assertEquals("", product.getCategory().getName());
    }

    @Test
    void testToProduct_WithEmptyStrings() {
        // Given
        FakeStoreProductResponseDTO dto = new FakeStoreProductResponseDTO();
        dto.setId(1L);
        dto.setTitle("");
        dto.setDescription("");
        dto.setPrice(0.0);
        dto.setImage("");
        dto.setCategory("");

        // When
        Product product = dto.toProduct();

        // Then
        assertNotNull(product);
        assertEquals(dto.getId(), product.getId());
        assertEquals("", product.getTitle());
        assertEquals("", product.getDescription());
        assertEquals(0.0, product.getPrice());
        assertEquals("", product.getImageUrl());
        assertEquals("", product.getCategory().getName());
    }
} 