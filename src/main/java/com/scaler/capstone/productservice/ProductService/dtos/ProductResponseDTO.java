package com.scaler.capstone.productservice.ProductService.dtos;

import com.scaler.capstone.productservice.ProductService.models.Category;
import com.scaler.capstone.productservice.ProductService.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String imageUrl;
    private CategoryDto category;

    public static ProductResponseDTO from(Product product) {
        return new ProductResponseDTO(product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl(),
                new CategoryDto(product.getCategory().getName(), product.getCategory().getDescription()));
    }

}
