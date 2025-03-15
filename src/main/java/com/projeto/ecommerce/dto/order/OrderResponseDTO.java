package com.projeto.ecommerce.dto.order;


import com.projeto.ecommerce.entity.Order;

import java.util.List;

public record OrderResponseDTO(
        String username,
        Long orderId,
        String orderDate,
        List<OrderProductResponse> productsProcessed,
        Double totalAmount,
        String status
) {
    public OrderResponseDTO(Order order, List<OrderProductResponse> responseProducts) {
        this(
                order.getUser().getUsername(),
                order.getId(),
                order.getOrderDate().toString(),
                responseProducts,
                order.getTotalAmount(),
                order.getStatus().toString()
        );
    }
}
