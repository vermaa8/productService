package com.scaler.capstone.productservice.ProductService.controller;

import com.scaler.capstone.productservice.ProductService.dtos.ProductResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @GetMapping("product/{id}")
    public ProductResponseDTO getProductById(@PathVariable("id") Long id) {
        ProductResponseDTO dummy = new ProductResponseDTO();
        dummy.setId(1L);
        dummy.setTitle("abc");
        dummy.setPrice(100D);
        dummy.setDescription("adj;lksadjf;lkjdnsf");
        dummy.setImageUrl("asldkj;lsdkjf");

        return dummy;
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
