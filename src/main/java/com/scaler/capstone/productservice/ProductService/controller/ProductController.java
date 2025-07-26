package com.scaler.capstone.productservice.ProductService.controller;

import com.scaler.capstone.productservice.ProductService.dtos.ProductResponseDTO;
import com.scaler.capstone.productservice.ProductService.models.Product;
import com.scaler.capstone.productservice.ProductService.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("product/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);

        return new ResponseEntity<>(ProductResponseDTO.from(product), HttpStatus.valueOf(202));
    }

    @GetMapping("product")
    public List<ProductResponseDTO> getAllProduct(){
        return null;
    }

    public void createProduct() {}

    public void deleteProduct() {}

    @GetMapping("/hello")
    public String greeting() {
        return "Hello World";
    }
}
