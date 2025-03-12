package com.projeto.ecommercee.dto.order;



import com.projeto.ecommercee.entity.OrderProduct;
import com.projeto.ecommercee.entity.Product;

import java.math.BigDecimal;
import java.util.List;

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
