package com.scaler.capstone.productservice.ProductService.dtos;

import com.scaler.capstone.productservice.ProductService.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponseDto {
    private User user;
}
