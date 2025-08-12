package com.scaler.capstone.productservice.ProductService.exceptions;

public class UserAlreadyPresentException extends RuntimeException {
    public UserAlreadyPresentException(String s) {
        super(s);
    }
}
