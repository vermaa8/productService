package com.scaler.capstone.productservice.ProductService.service;

import com.scaler.capstone.productservice.ProductService.dtos.FakeStoreProductRequestDTO;
import com.scaler.capstone.productservice.ProductService.dtos.FakeStoreProductResponseDTO;
import com.scaler.capstone.productservice.ProductService.exceptions.ProductNotFoundException;
import com.scaler.capstone.productservice.ProductService.models.Category;
import com.scaler.capstone.productservice.ProductService.models.Product;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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
        FakeStoreProductResponseDTO [] fakeStoreProductResponseDTOS
                = restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreProductResponseDTO [].class);
        List<Product> productList = new ArrayList<>();
        for (FakeStoreProductResponseDTO productResponseDTO: fakeStoreProductResponseDTOS) {
            productList.add(new Product(productResponseDTO.getId(),
                    productResponseDTO.getTitle(),
                    productResponseDTO.getDescription(),
                    productResponseDTO.getPrice(),
                    productResponseDTO.getImage(),
                    new Category(productResponseDTO.getCategory(),null)));
        }
        return productList;
    }

    @Override
    public Product createProduct(String title, String description, Double price, String imageUrl, String categoryName) {
        FakeStoreProductRequestDTO requestDto = new FakeStoreProductRequestDTO();
        requestDto.setTitle(title);
        requestDto.setDescription(description);
        requestDto.setCategory(categoryName);
        requestDto.setPrice(price);
        requestDto.setImage(imageUrl);

        FakeStoreProductResponseDTO responseDto = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                requestDto,
                FakeStoreProductResponseDTO.class
        );


        return responseDto.toProduct();
    }

    @Override
    public Product partialUpdate(Long id, Product product) throws ProductNotFoundException {
        HttpEntity<Product> httpEntity =
                new HttpEntity<>(product); // Add dto object here

        ResponseEntity<FakeStoreProductResponseDTO> responseEntity =
                restTemplate.exchange(
                        "https://fakestoreapi.com/products" + id,
                        HttpMethod.PATCH,
                        httpEntity, // use dto here
                        FakeStoreProductResponseDTO.class
                );

        FakeStoreProductResponseDTO responseDto = responseEntity.getBody();

        return null;
    }

}
