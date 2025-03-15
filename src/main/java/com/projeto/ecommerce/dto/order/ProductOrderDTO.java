package com.projeto.ecommerce.dto.order;

import com.projeto.ecommerce.entity.Product;
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
