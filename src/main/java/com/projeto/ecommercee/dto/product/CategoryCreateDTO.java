package com.projeto.ecommercee.dto.product;

import jakarta.validation.constraints.NotNull;

public record CategoryCreateDTO(@NotNull String name, @NotNull String description){
}
