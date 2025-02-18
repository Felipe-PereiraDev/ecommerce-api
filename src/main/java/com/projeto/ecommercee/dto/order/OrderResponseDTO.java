package com.projeto.ecommercee.dto.order;


import com.projeto.ecommercee.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        String username,
        Long orderId,
        LocalDateTime orderDate,
        List<OrderProductResponse> productsProcessed,
        Double totalAmount,
        String status
) {
    public OrderResponseDTO(Order order, List<OrderProductResponse> responseProducts) {
        this(
                order.getUser().getUsername(),
                order.getId(),
                order.getOrderDate(),
                responseProducts,
                order.getTotalAmount(),
                order.getStatus().toString()
        );
    }
}
