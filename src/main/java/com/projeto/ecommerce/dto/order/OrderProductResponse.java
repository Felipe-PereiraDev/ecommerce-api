package com.projeto.ecommerce.dto.order;



import com.projeto.ecommerce.entity.OrderProduct;

import java.math.BigDecimal;

public record OrderProductResponse(
        Long productId,
        String name,
        Long quantity,
        BigDecimal price
) {
    public OrderProductResponse(OrderProduct orderProduct) {
        this(
                orderProduct.getProduct().getId(),
                orderProduct.getProduct().getName(),
                orderProduct.getQuantity(),
                orderProduct.getPrice()
        );
    }


}
