package com.projeto.ecommercee.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryCreateDTO(
        @NotBlank @Size(min = 3, max = 80, message = "O nome da categoria tem que ter entre 3 e 30 caracteres.")
        String name,
        @NotBlank @Size(min = 10, max = 100, message = "A descrição tem que ter entre 10 a 100 caracteres caracteres.")
        String description
){
}
