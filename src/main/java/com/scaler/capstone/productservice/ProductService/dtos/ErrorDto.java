package com.scaler.capstone.productservice.ProductService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDto {
    private String status;
    private String message;
}
