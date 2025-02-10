package com.projeto.ecommercee.dto.order;



import com.projeto.ecommercee.entity.Product;

import java.math.BigDecimal;

public record OrderProductResponse(
        Long productId,
        String name,
        Long quantity,
        BigDecimal price
) {
    public OrderProductResponse(Product product, Long qtd) {
        this(
                product.getId(),
                product.getName(),
                qtd,
                product.getPrice()
        );
    }
}
