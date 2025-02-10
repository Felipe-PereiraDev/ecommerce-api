package com.projeto.ecommercee.dto.order;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderDTO(
        @NotNull
        List<ProductOrderDTO> products
) {
}
