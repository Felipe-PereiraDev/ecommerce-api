package com.projeto.ecommerce.dto.user;

public record UserUpdateDTO(
        String username,
        String email,
        String phone
) {
}
