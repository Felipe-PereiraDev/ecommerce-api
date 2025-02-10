package com.projeto.ecommercee.dto.user;

import com.projeto.ecommercee.entity.Order;

import java.time.LocalDateTime;

public record OrdersResponseDTO(
        String orderId,
        LocalDateTime orderDate,
        Double totalAmount,
        String status
) {
    public OrdersResponseDTO(Order order){
        this(
                order.getId().toString(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus().toString()
        );
    }
}
