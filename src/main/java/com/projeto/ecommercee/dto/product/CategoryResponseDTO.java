package com.projeto.ecommercee.dto.product;


import com.projeto.ecommercee.entity.Category;

public record CategoryResponseDTO(
        Long categoryId,
        String name,
        String description
) {
    public CategoryResponseDTO(Category category) {
        this(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}
