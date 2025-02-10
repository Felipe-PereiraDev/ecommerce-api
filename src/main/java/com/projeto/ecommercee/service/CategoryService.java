package com.projeto.ecommercee.service;


import com.projeto.ecommercee.dto.product.CategoryCreateDTO;
import com.projeto.ecommercee.entity.Category;
import com.projeto.ecommercee.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category createCategory(CategoryCreateDTO data) {
        Category category = new Category(null, data.name(), data.description(), null);
        return categoryRepository.save(category);
    }
}
