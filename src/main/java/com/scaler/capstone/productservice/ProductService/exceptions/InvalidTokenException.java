package com.scaler.capstone.productservice.ProductService.exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String invalidToken) {
        super(invalidToken);
    }
}
