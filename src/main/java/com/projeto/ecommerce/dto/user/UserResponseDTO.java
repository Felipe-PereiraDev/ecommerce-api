package com.projeto.ecommerce.dto.user;


import com.projeto.ecommerce.entity.Order;
import com.projeto.ecommerce.entity.Role;
import com.projeto.ecommerce.entity.User;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public record UserResponseDTO(
        String userId,
        String username,
        String email,
        Set<Role> roles,
        AddressResponseDTO address,
        String phone,
        List<OrdersResponseDTO> orders
) {
    public UserResponseDTO(User user) {
        this(
                user.getId().toString(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles(),
                user.getAddress() != null ? new AddressResponseDTO(user.getAddress()) : null,
                user.getPhone(),
                user.getOrders() != null ? user.getOrders().stream()
                        .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                        .map(OrdersResponseDTO::new).toList() : List.of()

        );
    }
}
