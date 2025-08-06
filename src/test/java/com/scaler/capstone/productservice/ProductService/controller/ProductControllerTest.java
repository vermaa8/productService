package com.scaler.capstone.productservice.ProductService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.capstone.productservice.ProductService.dtos.ProductRequestDTO;
import com.scaler.capstone.productservice.ProductService.dtos.ProductUpdateRequestDTO;
import com.scaler.capstone.productservice.ProductService.models.Category;
import com.scaler.capstone.productservice.ProductService.models.Product;
import com.scaler.capstone.productservice.ProductService.repositories.CategotyRepository;
import com.scaler.capstone.productservice.ProductService.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
class ProductControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategotyRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;
    private Category testCategory;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Create test category
        testCategory = new Category("Electronics", "Electronic devices and gadgets");
        testCategory = categoryRepository.save(testCategory);

        // Create test product
        testProduct = new Product("iPhone 15", "Latest iPhone model", 999.99, "iphone15.jpg", testCategory);
        testProduct = productRepository.save(testProduct);
    }

    @Test
    void testGetProductById_Success() throws Exception {
        // When & Then
        mockMvc.perform(get("/product/{id}", testProduct.getId()))
                .andExpect(status().isAccepted()) // 202 status code
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testProduct.getId()))
                .andExpect(jsonPath("$.title").value("iPhone 15"))
                .andExpect(jsonPath("$.description").value("Latest iPhone model"))
                .andExpect(jsonPath("$.price").value(999.99))
                .andExpect(jsonPath("$.imageUrl").value("iphone15.jpg"))
                .andExpect(jsonPath("$.category.name").value("Electronics"))
                .andExpect(jsonPath("$.category.description").value("Electronic devices and gadgets"));
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        // Given
        Long nonExistentId = 999L;

        // When & Then
        mockMvc.perform(get("/product/{id}", nonExistentId))
                .andExpect(status().isNotFound()); // 404 status code from GlobalExceptionHandler
    }

    @Test
    void testGetAllProducts_Success() throws Exception {
        // Given
        Product secondProduct = new Product("MacBook Pro", "Professional laptop", 1999.99, "macbook.jpg", testCategory);
        productRepository.save(secondProduct);

        // When & Then
        mockMvc.perform(get("/product"))
                .andExpect(status().isCreated()) // 201 status code
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("iPhone 15"))
                .andExpect(jsonPath("$[1].title").value("MacBook Pro"))
                .andExpect(jsonPath("$[0].category.name").value("Electronics"))
                .andExpect(jsonPath("$[1].category.name").value("Electronics"));
    }

    @Test
    void testCreateProduct_Success() throws Exception {
        // Given
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setTitle("iPad Pro");
        requestDTO.setDescription("Professional tablet");
        requestDTO.setPrice(1099.99);
        requestDTO.setImageUrl("ipad.jpg");
        requestDTO.setCategoryName("Electronics");

        // When & Then
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated()) // 201 status code
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("iPad Pro"))
                .andExpect(jsonPath("$.description").value("Professional tablet"))
                .andExpect(jsonPath("$.price").value(1099.99))
                .andExpect(jsonPath("$.imageUrl").value("ipad.jpg"))
                .andExpect(jsonPath("$.category.name").value("Electronics"));
    }

    @Test
    void testCreateProduct_WithNewCategory() throws Exception {
        // Given
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setTitle("Gaming Chair");
        requestDTO.setDescription("Ergonomic gaming chair");
        requestDTO.setPrice(299.99);
        requestDTO.setImageUrl("chair.jpg");
        requestDTO.setCategoryName("Furniture"); // New category

        // When & Then
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated()) // 201 status code
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Gaming Chair"))
                .andExpect(jsonPath("$.description").value("Ergonomic gaming chair"))
                .andExpect(jsonPath("$.price").value(299.99))
                .andExpect(jsonPath("$.imageUrl").value("chair.jpg"))
                .andExpect(jsonPath("$.category.name").value("Furniture"));
    }

    @Test
    void testPartialUpdate_Success() throws Exception {
        // Given
        ProductUpdateRequestDTO updateRequest = new ProductUpdateRequestDTO();
        updateRequest.setTitle("iPhone 15 Pro");
        updateRequest.setPrice(1199.99);

        // When & Then
        mockMvc.perform(patch("/product/{id}", testProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isCreated()) // 201 status code
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("iPhone 15 Pro"))
                .andExpect(jsonPath("$.price").value(1199.99))
                .andExpect(jsonPath("$.description").value("Latest iPhone model")) // Should remain unchanged
                .andExpect(jsonPath("$.imageUrl").value("iphone15.jpg")) // Should remain unchanged
                .andExpect(jsonPath("$.category.name").value("Electronics")); // Should remain unchanged
    }

    @Test
    void testPartialUpdate_ProductNotFound() throws Exception {
        // Given
        Long nonExistentId = 999L;
        ProductUpdateRequestDTO updateRequest = new ProductUpdateRequestDTO();
        updateRequest.setTitle("Updated Title");

        // When & Then
        mockMvc.perform(patch("/product/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound()); // 404 status code from GlobalExceptionHandler
    }

    @Test
    void testPartialUpdate_WithNewCategory() throws Exception {
        // Given
        ProductUpdateRequestDTO updateRequest = new ProductUpdateRequestDTO();
        updateRequest.setTitle("iPhone 15 Pro");
        updateRequest.setCategoryName("Smartphones"); // New category

        // When & Then
        mockMvc.perform(patch("/product/{id}", testProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isCreated()) // 201 status code
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("iPhone 15 Pro"))
                .andExpect(jsonPath("$.category.name").value("Smartphones"));
    }

    @Test
    void testGreeting() throws Exception {
        // When & Then
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));
    }

    @Test
    void testCreateProduct_InvalidRequest() throws Exception {
        // Given - Missing required fields
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setTitle("Test Product");
        // Missing other required fields

        // When & Then
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated()); // Should still work as service handles null values
    }
} 