package com.scaler.capstone.productservice.ProductService.dtos;

import com.scaler.capstone.productservice.ProductService.models.Category;
import com.scaler.capstone.productservice.ProductService.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String image;
    private String category;

    public static Product from(FakeStoreProductResponseDTO productResponseDTO) {
        return new Product(productResponseDTO.getId(),
                productResponseDTO.getTitle(),
                productResponseDTO.getDescription(),
                productResponseDTO.getPrice(),
                productResponseDTO.getImage(),
                new Category(productResponseDTO.getCategory(), null));
    }

    public Product toProduct() {
        Product product = new Product();
        product.setId(this.id);
        product.setTitle(this.title);
        product.setDescription(this.description);
        product.setImageUrl(this.image);
        product.setPrice(Double.valueOf(this.price));

        Category category1 = new Category();
        category1.setName(this.category);

        product.setCategory(category1);

        return product;
    }
}
