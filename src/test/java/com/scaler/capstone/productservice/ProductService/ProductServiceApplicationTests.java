package com.scaler.capstone.productservice.ProductService;

import com.scaler.capstone.productservice.ProductService.models.Category;
import com.scaler.capstone.productservice.ProductService.models.Product;
import com.scaler.capstone.productservice.ProductService.repositories.CategotyRepository;
import com.scaler.capstone.productservice.ProductService.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProductServiceApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategotyRepository categotyRepository;
	@Test
	void contextLoads() {
	}

	@Test
	public void test() {
		Optional<Category> category = categotyRepository.findByName("apple");
		List<Product> product = productRepository.findByCategory(category.get());
		System.out.println(product);
	}

	@Test
	public void testbySingleMethod() {

		List<Product> product = productRepository.findByCategory_NameEquals("apple");
		System.out.println(product);
	}

}
