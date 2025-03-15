package com.projeto.ecommerce.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductCreateDto(
        @NotBlank @Size(min = 3, max = 80, message = "O nome do produto tem que ter entre 3 e 80.")
        String name,
        @NotBlank @Size(min = 10, max = 255, message = "A descrição tem que ter entre 10 a 255 caracteres caracteres.")
        String description,
        @NotNull @Positive
        BigDecimal price,
        @NotNull @Positive
        int stockQuantity,
        @NotNull
        Long categoryId
) {
}
