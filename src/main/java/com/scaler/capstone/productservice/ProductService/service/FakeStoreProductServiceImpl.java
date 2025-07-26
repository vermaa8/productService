package com.scaler.capstone.productservice.ProductService.service;

import com.scaler.capstone.productservice.ProductService.dtos.FakeStoreProductResponseDTO;
import com.scaler.capstone.productservice.ProductService.exceptions.ProductNotFoundException;
import com.scaler.capstone.productservice.ProductService.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FakeStoreProductServiceImpl implements ProductService {
    private RestTemplate restTemplate;
    //private RedisTemplate<String, Object> redisTemplate;


    public FakeStoreProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        FakeStoreProductResponseDTO productResponseDTO=
            restTemplate.getForObject("https://fakestoreapi.com/products/"+id,
                    FakeStoreProductResponseDTO.class );
        return FakeStoreProductResponseDTO.from(productResponseDTO);

    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product createProduct(String title, String description, Double price, String imageUrl, String categoryName) {
        return null;
    }

    @Override
    public Product partialUpdate(Long id, Product product) throws ProductNotFoundException {
        return null;
    }

}
