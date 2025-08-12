package com.scaler.capstone.productservice.ProductService.controller;

import com.scaler.capstone.productservice.ProductService.dtos.*;
import com.scaler.capstone.productservice.ProductService.models.Token;
import com.scaler.capstone.productservice.ProductService.models.User;
import com.scaler.capstone.productservice.ProductService.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {

            User user = userService.signUp(
                    signupRequestDto.getName(),
                    signupRequestDto.getEmail(),
                    signupRequestDto.getPassword()
        );

        SignupResponseDto responseDto = new SignupResponseDto();
        responseDto.setUser(user);

        return responseDto;
    }

    @PostMapping("/login")
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Token token = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setToken(token);

        return responseDto;
    }
    @PostMapping("/validate")
    public UserDto validate(@RequestHeader("Authorization") String token) {
        User user = userService.validate(token);
        return UserDto.fromUser(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto) {
        userService.logout(logoutRequestDto.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
