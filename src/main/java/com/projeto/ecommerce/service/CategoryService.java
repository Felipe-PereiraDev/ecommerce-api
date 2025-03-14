package com.projeto.ecommerce.service;


import com.projeto.ecommerce.dto.category.CategoryCreateDTO;
import com.projeto.ecommerce.dto.category.CategoryUpdateDTO;
import com.projeto.ecommerce.entity.Category;
import com.projeto.ecommerce.exception.DatabaseException;
import com.projeto.ecommerce.repository.CategoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
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
        Category category = new Category(data.name(), data.description());
        return categoryRepository.save(category);
    }

    public Category updateCategory(CategoryUpdateDTO data, Long id) {
        var category = findById(id);
        categoryRepository.findByNameIgnoreCase(data.name()).ifPresent(cat -> {
            if (!cat.getId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma categoria com esse nome.");
            }
        });

        category.update(data);
        return categoryRepository.save(category);
    }


    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }
}
