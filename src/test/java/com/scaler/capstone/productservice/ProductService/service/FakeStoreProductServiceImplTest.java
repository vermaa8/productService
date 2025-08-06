package com.scaler.capstone.productservice.ProductService.service;

import com.scaler.capstone.productservice.ProductService.dtos.FakeStoreProductRequestDTO;
import com.scaler.capstone.productservice.ProductService.dtos.FakeStoreProductResponseDTO;
import com.scaler.capstone.productservice.ProductService.exceptions.ProductNotFoundException;
import com.scaler.capstone.productservice.ProductService.models.Category;
import com.scaler.capstone.productservice.ProductService.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FakeStoreProductServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FakeStoreProductServiceImpl fakeStoreProductService;

    private FakeStoreProductResponseDTO mockProductResponse;
    private FakeStoreProductResponseDTO[] mockProductArray;

    @BeforeEach
    void setUp() {
        // Setup mock product response
        mockProductResponse = new FakeStoreProductResponseDTO();
        mockProductResponse.setId(1L);
        mockProductResponse.setTitle("iPhone 15");
        mockProductResponse.setDescription("Latest iPhone model");
        mockProductResponse.setPrice(999.99);
        mockProductResponse.setImage("iphone15.jpg");
        mockProductResponse.setCategory("Electronics");

        // Setup mock product array for getAllProducts
        mockProductArray = new FakeStoreProductResponseDTO[2];
        mockProductArray[0] = mockProductResponse;
        
        FakeStoreProductResponseDTO secondProduct = new FakeStoreProductResponseDTO();
        secondProduct.setId(2L);
        secondProduct.setTitle("MacBook Pro");
        secondProduct.setDescription("Professional laptop");
        secondProduct.setPrice(1999.99);
        secondProduct.setImage("macbook.jpg");
        secondProduct.setCategory("Electronics");
        mockProductArray[1] = secondProduct;
    }

    @Test
    void testGetProductById_Success() throws ProductNotFoundException {
        // Given
        Long productId = 1L;
        when(restTemplate.getForObject(
                eq("https://fakestoreapi.com/products/" + productId),
                eq(FakeStoreProductResponseDTO.class)
        )).thenReturn(mockProductResponse);

        // When
        Product result = fakeStoreProductService.getProductById(productId);

        // Then
        assertNotNull(result);
        assertEquals(mockProductResponse.getId(), result.getId());
        assertEquals(mockProductResponse.getTitle(), result.getTitle());
        assertEquals(mockProductResponse.getDescription(), result.getDescription());
        assertEquals(mockProductResponse.getPrice(), result.getPrice());
        assertEquals(mockProductResponse.getImage(), result.getImageUrl());
        assertEquals(mockProductResponse.getCategory(), result.getCategory().getName());
        
        verify(restTemplate, times(1)).getForObject(
                eq("https://fakestoreapi.com/products/" + productId),
                eq(FakeStoreProductResponseDTO.class)
        );
    }

    @Test
    void testGetProductById_NotFound() {
        // Given
        Long productId = 999L;
        when(restTemplate.getForObject(
                eq("https://fakestoreapi.com/products/" + productId),
                eq(FakeStoreProductResponseDTO.class)
        )).thenReturn(null);

        // When & Then
        assertThrows(ProductNotFoundException.class, () -> {
            fakeStoreProductService.getProductById(productId);
        });
        
        verify(restTemplate, times(1)).getForObject(
                eq("https://fakestoreapi.com/products/" + productId),
                eq(FakeStoreProductResponseDTO.class)
        );
    }

    @Test
    void testGetProductById_RestClientException() {
        // Given
        Long productId = 1L;
        when(restTemplate.getForObject(
                eq("https://fakestoreapi.com/products/" + productId),
                eq(FakeStoreProductResponseDTO.class)
        )).thenThrow(new RestClientException("API Error"));

        // When & Then
        assertThrows(RestClientException.class, () -> {
            fakeStoreProductService.getProductById(productId);
        });
        
        verify(restTemplate, times(1)).getForObject(
                eq("https://fakestoreapi.com/products/" + productId),
                eq(FakeStoreProductResponseDTO.class)
        );
    }

    @Test
    void testGetAllProducts_Success() {
        // Given
        when(restTemplate.getForObject(
                eq("https://fakestoreapi.com/products"),
                eq(FakeStoreProductResponseDTO[].class)
        )).thenReturn(mockProductArray);

        // When
        List<Product> result = fakeStoreProductService.getAllProducts();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        
        // Verify first product
        Product firstProduct = result.get(0);
        assertEquals(mockProductArray[0].getId(), firstProduct.getId());
        assertEquals(mockProductArray[0].getTitle(), firstProduct.getTitle());
        assertEquals(mockProductArray[0].getDescription(), firstProduct.getDescription());
        assertEquals(mockProductArray[0].getPrice(), firstProduct.getPrice());
        assertEquals(mockProductArray[0].getImage(), firstProduct.getImageUrl());
        assertEquals(mockProductArray[0].getCategory(), firstProduct.getCategory().getName());
        
        // Verify second product
        Product secondProduct = result.get(1);
        assertEquals(mockProductArray[1].getId(), secondProduct.getId());
        assertEquals(mockProductArray[1].getTitle(), secondProduct.getTitle());
        assertEquals(mockProductArray[1].getDescription(), secondProduct.getDescription());
        assertEquals(mockProductArray[1].getPrice(), secondProduct.getPrice());
        assertEquals(mockProductArray[1].getImage(), secondProduct.getImageUrl());
        assertEquals(mockProductArray[1].getCategory(), secondProduct.getCategory().getName());
        
        verify(restTemplate, times(1)).getForObject(
                eq("https://fakestoreapi.com/products"),
                eq(FakeStoreProductResponseDTO[].class)
        );
    }

    @Test
    void testGetAllProducts_EmptyResponse() {
        // Given
        when(restTemplate.getForObject(
                eq("https://fakestoreapi.com/products"),
                eq(FakeStoreProductResponseDTO[].class)
        )).thenReturn(new FakeStoreProductResponseDTO[0]);

        // When
        List<Product> result = fakeStoreProductService.getAllProducts();

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
        
        verify(restTemplate, times(1)).getForObject(
                eq("https://fakestoreapi.com/products"),
                eq(FakeStoreProductResponseDTO[].class)
        );
    }

    @Test
    void testGetAllProducts_RestClientException() {
        // Given
        when(restTemplate.getForObject(
                eq("https://fakestoreapi.com/products"),
                eq(FakeStoreProductResponseDTO[].class)
        )).thenThrow(new RestClientException("API Error"));

        // When & Then
        assertThrows(RestClientException.class, () -> {
            fakeStoreProductService.getAllProducts();
        });
        
        verify(restTemplate, times(1)).getForObject(
                eq("https://fakestoreapi.com/products"),
                eq(FakeStoreProductResponseDTO[].class)
        );
    }

    @Test
    void testCreateProduct_Success() {
        // Given
        String title = "iPad Pro";
        String description = "Professional tablet";
        Double price = 1099.99;
        String imageUrl = "ipad.jpg";
        String categoryName = "Electronics";

        when(restTemplate.postForObject(
                eq("https://fakestoreapi.com/products"),
                any(FakeStoreProductRequestDTO.class),
                eq(FakeStoreProductResponseDTO.class)
        )).thenReturn(mockProductResponse);

        // When
        Product result = fakeStoreProductService.createProduct(title, description, price, imageUrl, categoryName);

        // Then
        assertNotNull(result);
        assertEquals(mockProductResponse.getId(), result.getId());
        assertEquals(mockProductResponse.getTitle(), result.getTitle());
        assertEquals(mockProductResponse.getDescription(), result.getDescription());
        assertEquals(mockProductResponse.getPrice(), result.getPrice());
        assertEquals(mockProductResponse.getImage(), result.getImageUrl());
        assertEquals(mockProductResponse.getCategory(), result.getCategory().getName());
        
        // Verify the request DTO was created correctly
        verify(restTemplate, times(1)).postForObject(
                eq("https://fakestoreapi.com/products"),
                argThat(requestDto -> {
                    FakeStoreProductRequestDTO dto = (FakeStoreProductRequestDTO) requestDto;
                    return title.equals(dto.getTitle()) &&
                           description.equals(dto.getDescription()) &&
                           price.equals(dto.getPrice()) &&
                           imageUrl.equals(dto.getImage()) &&
                           categoryName.equals(dto.getCategory());
                }),
                eq(FakeStoreProductResponseDTO.class)
        );
    }

    @Test
    void testCreateProduct_WithNullValues() {
        // Given
        String title = "Test Product";
        String description = null;
        Double price = null;
        String imageUrl = null;
        String categoryName = null;

        when(restTemplate.postForObject(
                eq("https://fakestoreapi.com/products"),
                any(FakeStoreProductRequestDTO.class),
                eq(FakeStoreProductResponseDTO.class)
        )).thenReturn(mockProductResponse);

        // When
        Product result = fakeStoreProductService.createProduct(title, description, price, imageUrl, categoryName);

        // Then
        assertNotNull(result);
        assertEquals(mockProductResponse.getId(), result.getId());
        
        // Verify the request DTO was created correctly with null values
        verify(restTemplate, times(1)).postForObject(
                eq("https://fakestoreapi.com/products"),
                argThat(requestDto -> {
                    FakeStoreProductRequestDTO dto = (FakeStoreProductRequestDTO) requestDto;
                    return title.equals(dto.getTitle()) &&
                           dto.getDescription() == null &&
                           dto.getPrice() == null &&
                           dto.getImage() == null &&
                           dto.getCategory() == null;
                }),
                eq(FakeStoreProductResponseDTO.class)
        );
    }

    @Test
    void testCreateProduct_RestClientException() {
        // Given
        String title = "iPad Pro";
        String description = "Professional tablet";
        Double price = 1099.99;
        String imageUrl = "ipad.jpg";
        String categoryName = "Electronics";

        when(restTemplate.postForObject(
                eq("https://fakestoreapi.com/products"),
                any(FakeStoreProductRequestDTO.class),
                eq(FakeStoreProductResponseDTO.class)
        )).thenThrow(new RestClientException("API Error"));

        // When & Then
        assertThrows(RestClientException.class, () -> {
            fakeStoreProductService.createProduct(title, description, price, imageUrl, categoryName);
        });
        
        verify(restTemplate, times(1)).postForObject(
                eq("https://fakestoreapi.com/products"),
                any(FakeStoreProductRequestDTO.class),
                eq(FakeStoreProductResponseDTO.class)
        );
    }

    /*@Test
    void testPartialUpdate_ReturnsNull() throws ProductNotFoundException {
        // Given
        Long productId = 1L;
        Product product = new Product();
        product.setTitle("Updated Title");

        // When
        Product result = fakeStoreProductService.partialUpdate(productId, product);

        // Then
        assertNull(result);
    }*/

    @Test
    void testPartialUpdate_WithCategoryName_ReturnsNull() throws ProductNotFoundException {
        // Given
        Long productId = 1L;
        Product product = new Product();
        product.setTitle("Updated Title");
        String categoryName = "Electronics";

        // When
        Product result = fakeStoreProductService.partialUpdate(productId, product, categoryName);

        // Then
        assertNull(result);
    }

    @Test
    void testFakeStoreProductResponseDTO_FromMethod() {
        // Given
        FakeStoreProductResponseDTO dto = new FakeStoreProductResponseDTO();
        dto.setId(1L);
        dto.setTitle("Test Product");
        dto.setDescription("Test Description");
        dto.setPrice(99.99);
        dto.setImage("test.jpg");
        dto.setCategory("Test Category");

        // When
        Product product = FakeStoreProductResponseDTO.from(dto);

        // Then
        assertNotNull(product);
        assertEquals(dto.getId(), product.getId());
        assertEquals(dto.getTitle(), product.getTitle());
        assertEquals(dto.getDescription(), product.getDescription());
        assertEquals(dto.getPrice(), product.getPrice());
        assertEquals(dto.getImage(), product.getImageUrl());
        assertEquals(dto.getCategory(), product.getCategory().getName());
        assertNull(product.getCategory().getDescription());
    }

    @Test
    void testFakeStoreProductResponseDTO_ToProductMethod() {
        // Given
        FakeStoreProductResponseDTO dto = new FakeStoreProductResponseDTO();
        dto.setId(1L);
        dto.setTitle("Test Product");
        dto.setDescription("Test Description");
        dto.setPrice(99.99);
        dto.setImage("test.jpg");
        dto.setCategory("Test Category");

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
} 