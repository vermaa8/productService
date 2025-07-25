package com.scaler.capstone.productservice.ProductService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {
    private String title;
    private String description;
    private Double price;
    private String imageUrl;
    private String categoryName;
}