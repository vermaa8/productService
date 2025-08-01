package com.scaler.capstone.productservice.ProductService.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product extends BaseModel {
    private String title;
    private String description;
    private Double price;
    private String imageUrl;
    @ManyToOne(cascade = CascadeType.ALL) // or CascadeType.ALL
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(Long id,String title, String description, Double price, String imageUrl, Category category) {
        super.setId(id);
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public Product(String title, String description, Double price, String imageUrl, Category category) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }
}
