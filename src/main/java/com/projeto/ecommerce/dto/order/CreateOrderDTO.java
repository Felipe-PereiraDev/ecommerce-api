package com.projeto.ecommerce.dto.order;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderDTO(
        @NotNull
        List<ProductOrderDTO> products
) {
}
