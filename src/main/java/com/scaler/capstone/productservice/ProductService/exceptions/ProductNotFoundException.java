package com.scaler.capstone.productservice.ProductService.exceptions;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException() {
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
