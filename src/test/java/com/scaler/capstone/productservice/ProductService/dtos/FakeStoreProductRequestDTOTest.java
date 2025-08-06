package com.scaler.capstone.productservice.ProductService.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FakeStoreProductRequestDTOTest {

    @Test
    void testSettersAndGetters_WithValidData() {
        // Given
        FakeStoreProductRequestDTO dto = new FakeStoreProductRequestDTO();
        String title = "iPhone 15";
        Double price = 999.99;
        String category = "Electronics";
        String description = "Latest iPhone model";
        String image = "iphone15.jpg";

        // When
        dto.setTitle(title);
        dto.setPrice(price);
        dto.setCategory(category);
        dto.setDescription(description);
        dto.setImage(image);

        // Then
        assertEquals(title, dto.getTitle());
        assertEquals(price, dto.getPrice());
        assertEquals(category, dto.getCategory());
        assertEquals(description, dto.getDescription());
        assertEquals(image, dto.getImage());
    }

    @Test
    void testSettersAndGetters_WithNullValues() {
        // Given
        FakeStoreProductRequestDTO dto = new FakeStoreProductRequestDTO();

        // When
        dto.setTitle(null);
        dto.setPrice(null);
        dto.setCategory(null);
        dto.setDescription(null);
        dto.setImage(null);

        // Then
        assertNull(dto.getTitle());
        assertNull(dto.getPrice());
        assertNull(dto.getCategory());
        assertNull(dto.getDescription());
        assertNull(dto.getImage());
    }

    @Test
    void testSettersAndGetters_WithEmptyStrings() {
        // Given
        FakeStoreProductRequestDTO dto = new FakeStoreProductRequestDTO();
        String emptyString = "";

        // When
        dto.setTitle(emptyString);
        dto.setCategory(emptyString);
        dto.setDescription(emptyString);
        dto.setImage(emptyString);
        dto.setPrice(0.0);

        // Then
        assertEquals(emptyString, dto.getTitle());
        assertEquals(emptyString, dto.getCategory());
        assertEquals(emptyString, dto.getDescription());
        assertEquals(emptyString, dto.getImage());
        assertEquals(0.0, dto.getPrice());
    }

    @Test
    void testSettersAndGetters_WithZeroPrice() {
        // Given
        FakeStoreProductRequestDTO dto = new FakeStoreProductRequestDTO();
        Double zeroPrice = 0.0;

        // When
        dto.setPrice(zeroPrice);

        // Then
        assertEquals(zeroPrice, dto.getPrice());
    }

    @Test
    void testSettersAndGetters_WithNegativePrice() {
        // Given
        FakeStoreProductRequestDTO dto = new FakeStoreProductRequestDTO();
        Double negativePrice = -10.0;

        // When
        dto.setPrice(negativePrice);

        // Then
        assertEquals(negativePrice, dto.getPrice());
    }

    @Test
    void testSettersAndGetters_WithSpecialCharacters() {
        // Given
        FakeStoreProductRequestDTO dto = new FakeStoreProductRequestDTO();
        String titleWithSpecialChars = "iPhone 15 Pro Max (256GB)";
        String descriptionWithSpecialChars = "Latest iPhone model with A17 Pro chip & 48MP camera!";
        String categoryWithSpecialChars = "Smartphones & Mobile";
        String imageWithSpecialChars = "iphone15-pro-max-256gb.jpg";

        // When
        dto.setTitle(titleWithSpecialChars);
        dto.setDescription(descriptionWithSpecialChars);
        dto.setCategory(categoryWithSpecialChars);
        dto.setImage(imageWithSpecialChars);
        dto.setPrice(1199.99);

        // Then
        assertEquals(titleWithSpecialChars, dto.getTitle());
        assertEquals(descriptionWithSpecialChars, dto.getDescription());
        assertEquals(categoryWithSpecialChars, dto.getCategory());
        assertEquals(imageWithSpecialChars, dto.getImage());
        assertEquals(1199.99, dto.getPrice());
    }

    @Test
    void testSettersAndGetters_WithLongStrings() {
        // Given
        FakeStoreProductRequestDTO dto = new FakeStoreProductRequestDTO();
        String longTitle = "This is a very long product title that might exceed normal length limits and should be handled properly by the system";
        String longDescription = "This is a very long product description that contains a lot of details about the product features, specifications, and benefits. It might contain multiple paragraphs and should be handled properly by the system without any issues.";
        String longCategory = "This is a very long category name that might be used for categorization purposes";
        String longImage = "this-is-a-very-long-image-url-that-might-be-used-for-storing-product-images-in-a-content-delivery-network.jpg";

        // When
        dto.setTitle(longTitle);
        dto.setDescription(longDescription);
        dto.setCategory(longCategory);
        dto.setImage(longImage);
        dto.setPrice(999.99);

        // Then
        assertEquals(longTitle, dto.getTitle());
        assertEquals(longDescription, dto.getDescription());
        assertEquals(longCategory, dto.getCategory());
        assertEquals(longImage, dto.getImage());
        assertEquals(999.99, dto.getPrice());
    }

    @Test
    void testSettersAndGetters_WithUnicodeCharacters() {
        // Given
        FakeStoreProductRequestDTO dto = new FakeStoreProductRequestDTO();
        String titleWithUnicode = "iPhone 15 Pro Max - 最新款";
        String descriptionWithUnicode = "最新款iPhone，配备A17 Pro芯片和48MP摄像头！";
        String categoryWithUnicode = "智能手机";
        String imageWithUnicode = "iphone15-pro-max-最新款.jpg";

        // When
        dto.setTitle(titleWithUnicode);
        dto.setDescription(descriptionWithUnicode);
        dto.setCategory(categoryWithUnicode);
        dto.setImage(imageWithUnicode);
        dto.setPrice(1299.99);

        // Then
        assertEquals(titleWithUnicode, dto.getTitle());
        assertEquals(descriptionWithUnicode, dto.getDescription());
        assertEquals(categoryWithUnicode, dto.getCategory());
        assertEquals(imageWithUnicode, dto.getImage());
        assertEquals(1299.99, dto.getPrice());
    }
} 