package com.projeto.ecommercee.controller;

import com.projeto.ecommercee.dto.product.CategoryCreateDTO;
import com.projeto.ecommercee.dto.product.CategoryResponseDTO;
import com.projeto.ecommercee.entity.Category;
import com.projeto.ecommercee.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> listCategory(){
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(categories.stream().map(CategoryResponseDTO::new).toList());
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody @Validated CategoryCreateDTO data){

        var category = categoryService.createCategory(data);
        return ResponseEntity.ok().body(new CategoryResponseDTO(category));
    }
}
