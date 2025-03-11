package com.projeto.ecommercee.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(
        @NotBlank @Size(min = 3, max = 30, message = "O username tem que ter entre 3 e 30 caracteres.")
        String username,
        @NotBlank @Email @Size(min = 5, max = 80, message = "O email tem que ter entre 5 e 80 caracteres.")
        String email,
        @NotBlank @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
        String password,
        @NotBlank @Size(min = 4, max = 15, message = "O telefone tem que ter ente 4 e 15 números.")
        String phone
) {
}
