package com.scaler.capstone.productservice.ProductService.service;

import com.scaler.capstone.productservice.ProductService.exceptions.InvalidTokenException;
import com.scaler.capstone.productservice.ProductService.exceptions.UserAlreadyPresentException;
import com.scaler.capstone.productservice.ProductService.exceptions.UserNotPresentException;
import com.scaler.capstone.productservice.ProductService.exceptions.WrongPasswordException;
import com.scaler.capstone.productservice.ProductService.models.Token;
import com.scaler.capstone.productservice.ProductService.models.User;
import com.scaler.capstone.productservice.ProductService.repositories.TokenRepository;
import com.scaler.capstone.productservice.ProductService.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{


    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private TokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User signUp(String name, String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            throw new UserAlreadyPresentException("User with this email is already existing");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public Token login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UserNotPresentException("User with this email is not present");
        }
        User user = optionalUser.get();
        if (!bCryptPasswordEncoder.matches(password, user.getHashedPassword())) {
            throw new WrongPasswordException("Password is incorrect");
        }
        Token token = createToken(user);
        tokenRepository.save(token);
        return null;
    }

    private Token createToken(User user) {
        Token token = new Token();

        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        token.setUser(user);
        token.setExpiryAt(DateUtils.addDays(new Date(), 30));
        token.setDeleted(false);
        return token;
    }
    @Override
    public User validate(String token) {
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(
                token, false, new Date()
        );
        if (optionalToken.isEmpty()) {
            throw new InvalidTokenException("Invalid token");
        }
        Token token1= optionalToken.get();

        return token1.getUser();
    }

    @Override
    public void logout(String tokenValue) {
        Optional<Token> optionalToken = tokenRepository
                .findByValueAndDeleted(tokenValue, false);

        if (optionalToken.isEmpty()) {
            throw new InvalidTokenException("Invalid token");
        }

        Token token = optionalToken.get();

        token.setDeleted(true);
        tokenRepository.save(token);
    }
}
