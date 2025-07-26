package com.scaler.capstone.productservice.ProductService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductRequestDTO {
    private String title;
    private Double price;
    private String category;
    private String description;
    private String image;
}