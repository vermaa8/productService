package com.scaler.capstone.productservice.ProductService.service;

import com.scaler.capstone.productservice.ProductService.models.Token;
import com.scaler.capstone.productservice.ProductService.models.User;

public interface UserService {
    public User signUp(String name, String email, String password);
    public Token login(String email, String password);
    public User validate(String token);
    public void logout(String token);
}
