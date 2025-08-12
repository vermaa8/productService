package com.scaler.capstone.productservice.ProductService.dtos;

import com.scaler.capstone.productservice.ProductService.models.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private Token token;
}
