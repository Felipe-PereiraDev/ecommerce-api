package com.projeto.ecommerce.dto.category;


import com.projeto.ecommerce.entity.Category;

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
