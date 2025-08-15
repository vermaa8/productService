package com.scaler.capstone.productservice.ProductService.service;

import com.scaler.capstone.productservice.ProductService.models.Token;
import com.scaler.capstone.productservice.ProductService.models.User;

public interface UserService {
    User signUp(String name, String email, String password);
    Token login(String email, String password);
    User validate(String token);
    void logout(String token);
}
