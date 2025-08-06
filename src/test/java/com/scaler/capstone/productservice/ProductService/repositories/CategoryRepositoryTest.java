package com.scaler.capstone.productservice.ProductService.repositories;

import com.scaler.capstone.productservice.ProductService.models.Category;
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
class CategoryRepositoryTest {

    @Autowired
    private CategotyRepository categoryRepository;

    private Category electronicsCategory;
    private Category furnitureCategory;
    private Category clothingCategory;

    @BeforeEach
    void setUp() {
        // Create test categories
        electronicsCategory = new Category("Electronics", "Electronic devices and gadgets");
        furnitureCategory = new Category("Furniture", "Home and office furniture");
        clothingCategory = new Category("Clothing", "Apparel and accessories");

        electronicsCategory = categoryRepository.save(electronicsCategory);
        furnitureCategory = categoryRepository.save(furnitureCategory);
        clothingCategory = categoryRepository.save(clothingCategory);
    }

    @Test
    void testSaveCategory() {
        // Given
        Category newCategory = new Category("Books", "Books and publications");

        // When
        Category savedCategory = categoryRepository.save(newCategory);

        // Then
        assertNotNull(savedCategory);
        assertNotNull(savedCategory.getId());
        assertEquals("Books", savedCategory.getName());
        assertEquals("Books and publications", savedCategory.getDescription());
    }

    @Test
    void testFindById_Success() {
        // When
        Optional<Category> foundCategory = categoryRepository.findById(electronicsCategory.getId());

        // Then
        assertTrue(foundCategory.isPresent());
        assertEquals(electronicsCategory.getId(), foundCategory.get().getId());
        assertEquals("Electronics", foundCategory.get().getName());
        assertEquals("Electronic devices and gadgets", foundCategory.get().getDescription());
    }

    @Test
    void testFindById_NotFound() {
        // Given
        Long nonExistentId = 999L;

        // When
        Optional<Category> foundCategory = categoryRepository.findById(nonExistentId);

        // Then
        assertFalse(foundCategory.isPresent());
    }

    @Test
    void testFindAll() {
        // When
        List<Category> categories = categoryRepository.findAll();

        // Then
        assertNotNull(categories);
        assertEquals(3, categories.size());
        assertTrue(categories.stream().anyMatch(c -> c.getName().equals("Electronics")));
        assertTrue(categories.stream().anyMatch(c -> c.getName().equals("Furniture")));
        assertTrue(categories.stream().anyMatch(c -> c.getName().equals("Clothing")));
    }

    @Test
    void testFindByName_Success() {
        // When
        Optional<Category> foundCategory = categoryRepository.findByName("Electronics");

        // Then
        assertTrue(foundCategory.isPresent());
        assertEquals("Electronics", foundCategory.get().getName());
        assertEquals("Electronic devices and gadgets", foundCategory.get().getDescription());
        assertEquals(electronicsCategory.getId(), foundCategory.get().getId());
    }

    @Test
    void testFindByName_NotFound() {
        // Given
        String nonExistentName = "NonExistentCategory";

        // When
        Optional<Category> foundCategory = categoryRepository.findByName(nonExistentName);

        // Then
        assertFalse(foundCategory.isPresent());
    }

    @Test
    void testFindByName_CaseSensitive() {
        // Given
        String lowercaseName = "electronics"; // Different case

        // When
        Optional<Category> foundCategory = categoryRepository.findByName(lowercaseName);

        // Then
        assertFalse(foundCategory.isPresent()); // Should not find due to case sensitivity
    }

    @Test
    void testUpdateCategory() {
        // Given
        Category categoryToUpdate = categoryRepository.findById(electronicsCategory.getId()).get();
        categoryToUpdate.setName("Smart Electronics");
        categoryToUpdate.setDescription("Smart electronic devices and IoT gadgets");

        // When
        Category updatedCategory = categoryRepository.save(categoryToUpdate);

        // Then
        assertNotNull(updatedCategory);
        assertEquals(electronicsCategory.getId(), updatedCategory.getId());
        assertEquals("Smart Electronics", updatedCategory.getName());
        assertEquals("Smart electronic devices and IoT gadgets", updatedCategory.getDescription());
    }

    @Test
    void testDeleteCategory() {
        // Given
        Long categoryId = electronicsCategory.getId();

        // When
        categoryRepository.deleteById(categoryId);

        // Then
        Optional<Category> deletedCategory = categoryRepository.findById(categoryId);
        assertFalse(deletedCategory.isPresent());

        List<Category> remainingCategories = categoryRepository.findAll();
        assertEquals(2, remainingCategories.size());
        assertFalse(remainingCategories.stream().anyMatch(c -> c.getId().equals(categoryId)));
    }

    @Test
    void testCount() {
        // When
        long count = categoryRepository.count();

        // Then
        assertEquals(3, count);
    }

    @Test
    void testExistsById() {
        // When & Then
        assertTrue(categoryRepository.existsById(electronicsCategory.getId()));
        assertTrue(categoryRepository.existsById(furnitureCategory.getId()));
        assertTrue(categoryRepository.existsById(clothingCategory.getId()));
        assertFalse(categoryRepository.existsById(999L));
    }

    @Test
    void testSaveAll() {
        // Given
        Category category1 = new Category("Sports", "Sports equipment");
        Category category2 = new Category("Food", "Food and beverages");
        List<Category> categoriesToSave = List.of(category1, category2);

        // When
        List<Category> savedCategories = categoryRepository.saveAll(categoriesToSave);

        // Then
        assertNotNull(savedCategories);
        assertEquals(2, savedCategories.size());
        assertTrue(savedCategories.stream().allMatch(c -> c.getId() != null));
        assertTrue(savedCategories.stream().anyMatch(c -> c.getName().equals("Sports")));
        assertTrue(savedCategories.stream().anyMatch(c -> c.getName().equals("Food")));

        // Verify total count
        assertEquals(5, categoryRepository.count());
    }

    @Test
    void testDeleteAll() {
        // When
        categoryRepository.deleteAll();

        // Then
        assertEquals(0, categoryRepository.count());
        assertTrue(categoryRepository.findAll().isEmpty());
    }

    @Test
    void testDeleteAllById() {
        // Given
        List<Long> idsToDelete = List.of(electronicsCategory.getId(), furnitureCategory.getId());

        // When
        categoryRepository.deleteAllById(idsToDelete);

        // Then
        assertEquals(1, categoryRepository.count());
        assertFalse(categoryRepository.existsById(electronicsCategory.getId()));
        assertFalse(categoryRepository.existsById(furnitureCategory.getId()));
        assertTrue(categoryRepository.existsById(clothingCategory.getId()));
    }
} 