package com.projeto.ecommerce.dto.product;

import java.math.BigDecimal;

public record ProductUpdateDTO(
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        Long categoryId
) {

}
