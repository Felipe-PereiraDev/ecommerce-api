package com.projeto.ecommercee.dto.user;

import com.projeto.ecommercee.entity.Order;

import java.time.LocalDateTime;

public record OrdersResponseDTO(
        Long orderId,
        String orderDate,
        Double totalAmount,
        String status
) {
    public OrdersResponseDTO(Order order){
        this(
                order.getId(),
                order.getOrderDate().toString(),
                order.getTotalAmount(),
                order.getStatus().toString()
        );
    }
}
