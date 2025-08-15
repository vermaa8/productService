package com.scaler.capstone.productservice.ProductService.repositories;

import com.scaler.capstone.productservice.ProductService.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValueAndIsDeletedAndExpiryAtGreaterThan(String value, Boolean deleted, Date date);

    Optional<Token> findByValueAndIsDeleted(String token, boolean b);
}
