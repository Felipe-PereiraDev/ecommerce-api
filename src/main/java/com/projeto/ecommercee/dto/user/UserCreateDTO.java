package com.projeto.ecommercee.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateDTO(
        @NotBlank
        String username,
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank
        String phone
) {
}
