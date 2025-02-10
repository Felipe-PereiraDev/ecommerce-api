package com.projeto.ecommercee.dto.product;

import com.projeto.ecommercee.entity.Product;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        String category

) {
    public ProductResponseDTO(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCategory().getName()
        );
    }
}
