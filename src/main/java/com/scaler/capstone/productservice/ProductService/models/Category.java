package com.scaler.capstone.productservice.ProductService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category extends BaseModel {
    private String name;
    private String description;

    @OneToMany
    private List<Product> featuredProducts;

    @OneToMany(mappedBy = "category")
    private List <Product> products;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
