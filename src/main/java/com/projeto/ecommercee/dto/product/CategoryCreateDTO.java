package com.projeto.ecommercee.dto.product;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateDTO(@NotBlank String name, @NotBlank String description){
}
