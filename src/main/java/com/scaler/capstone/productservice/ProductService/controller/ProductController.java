package com.scaler.capstone.productservice.ProductService.controller;

import com.scaler.capstone.productservice.ProductService.dtos.CategoryDto;
import com.scaler.capstone.productservice.ProductService.dtos.ProductRequestDTO;
import com.scaler.capstone.productservice.ProductService.dtos.ProductResponseDTO;
import com.scaler.capstone.productservice.ProductService.exceptions.ProductNotFoundException;
import com.scaler.capstone.productservice.ProductService.models.Product;
import com.scaler.capstone.productservice.ProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {


    private ProductService productService;

    public ProductController(@Qualifier("productDBService") ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("product/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(ProductResponseDTO.from(product), HttpStatus.valueOf(202));
    }

    @GetMapping("product")
    public ResponseEntity<List<ProductResponseDTO>> getAllProduct(){
        List<Product> products = productService.getAllProducts();
        List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();
        for (Product product :products) {
            productResponseDTOS.add(new ProductResponseDTO(product.getId(),
                    product.getTitle(), product.getDescription(),
                    product.getPrice(),
                    product.getImageUrl(),
                    new CategoryDto(product.getCategory().getName(), product.getCategory().getDescription())));

        }
        return new ResponseEntity<>(productResponseDTOS, HttpStatus.valueOf(201));
    }

    @PostMapping("product")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        Product product = productService.createProduct(productRequestDTO.getTitle(),
                productRequestDTO.getDescription(),
                productRequestDTO.getPrice(),
                productRequestDTO.getImageUrl(),
                productRequestDTO.getCategoryName());
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl(),
                new CategoryDto(product.getCategory().getName(), product.getCategory().getDescription()));
        return new ResponseEntity<>(productResponseDTO, HttpStatus.valueOf(201));
    }


    @PatchMapping("product")
    public ResponseEntity<ProductResponseDTO> partialUpdate(Long id, Product product) throws ProductNotFoundException {
        Product product1=  productService.partialUpdate(id, product);
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(product1.getId(),
                product1.getTitle(),
                product1.getDescription(),
                product1.getPrice(),
                product1.getImageUrl(),
                new CategoryDto(product1.getCategory().getName(), product1.getCategory().getDescription()));
        return new ResponseEntity<>(productResponseDTO, HttpStatus.valueOf(201));
    }


    public void deleteProduct() {}

    @GetMapping("/hello")
    public String greeting() {
        return "Hello World";
    }
}
