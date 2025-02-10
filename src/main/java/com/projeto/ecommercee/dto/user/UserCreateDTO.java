package com.projeto.ecommercee.dto.user;

public record UserCreateDTO(
        String username,
        String email,
        String password,
        String phone
) {
}
