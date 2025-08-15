package com.scaler.capstone.productservice.ProductService.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

    @Getter
    @Setter
    @MappedSuperclass
    public class BaseModel {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;
        @Column(name = "updated_at", nullable = false)
        private LocalDateTime updatedAt;
        @Column(name = "is_deleted", nullable = false)
        private boolean isDeleted = false;
        @PrePersist
        protected  void onCreate() {
            LocalDateTime localDateTime = LocalDateTime.now();
            this.createdAt = localDateTime;
            this.updatedAt = localDateTime;
        }
        @PreUpdate
        protected void onUpdate() {
            this.updatedAt = LocalDateTime.now();
        }
    }
