package com.projeto.ecommercee.dto.user;

import com.projeto.ecommercee.entity.Order;

import java.time.LocalDateTime;

public record OrdersResponseDTO(
        Long orderId,
        LocalDateTime orderDate,
        Double totalAmount,
        String status
) {
    public OrdersResponseDTO(Order order){
        this(
                order.getId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus().toString()
        );
    }
}
