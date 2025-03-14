package com.projeto.ecommercee.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para criação de um novo usuário")
public record UserCreateDTO(
        @Schema(description = "Nome de usuário", example = "silva123")
        @NotBlank @Size(min = 3, max = 30, message = "O username tem que ter entre 3 e 30 caracteres.")
        String username,

        @Schema(description = "Email do usuário", example = "silva123@gmail.com")
        @NotBlank @Email @Size(min = 5, max = 80, message = "O email tem que ter entre 5 e 80 caracteres.")
        String email,

        @Schema(description = "Senha de usuário", example = "senha123")
        @NotBlank @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
        String password,

        @Schema(description = "Telefone do usuário", example = "4002-8922")
        @NotBlank @Size(min = 4, max = 15, message = "O telefone tem que ter ente 4 e 15 números.")
        String phone
) {
}
