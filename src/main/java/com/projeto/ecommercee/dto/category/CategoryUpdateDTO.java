package com.projeto.ecommercee.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryUpdateDTO(
        String name,
        String description
) {
}
