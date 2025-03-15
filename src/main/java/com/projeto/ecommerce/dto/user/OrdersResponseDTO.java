package com.projeto.ecommerce.dto.user;

import com.projeto.ecommerce.entity.Order;

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
