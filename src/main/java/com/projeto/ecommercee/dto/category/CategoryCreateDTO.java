package com.projeto.ecommercee.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryCreateDTO(
        @NotBlank @Size(min = 3, max = 80, message = "O nome da categoria tem que ter entre 3 e 80 caracteres.")
        String name,
        @NotBlank @Size(min = 10, max = 255, message = "A descrição tem que ter entre 10 a 255 caracteres caracteres.")
        String description
){
}
