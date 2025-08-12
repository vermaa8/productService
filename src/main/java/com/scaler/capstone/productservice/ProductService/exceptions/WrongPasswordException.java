package com.scaler.capstone.productservice.ProductService.exceptions;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException(String passwordIsIncorrect) {
        super(passwordIsIncorrect);
    }
}
