package com.scaler.capstone.productservice.ProductService.exceptions;

public class UserNotPresentException extends RuntimeException {
    public UserNotPresentException(String s) {
        super(s);
    }
}
