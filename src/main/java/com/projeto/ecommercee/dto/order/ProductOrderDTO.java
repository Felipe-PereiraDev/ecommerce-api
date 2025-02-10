package com.projeto.ecommercee.dto.order;

import com.projeto.ecommercee.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductOrderDTO(
        @NotBlank
        Long productId,
        @NotNull
        Long quantity
) {
        public ProductOrderDTO(Product product) {
                this(
                        product.getId(),
                        (long) product.getStockQuantity()
                );
        }
}
