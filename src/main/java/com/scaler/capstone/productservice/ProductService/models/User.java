package com.scaler.capstone.productservice.ProductService.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "users")
@Table(name = "users")
public class User extends BaseModel{
    private String name;
    @Column(name = "email")
    private String email;
    private String hashedPassword;

    @ManyToMany
    private List<Role> roles;
}
