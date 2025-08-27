package com.projeto.ecommerce.controller;

import com.projeto.ecommerce.controller.docs.CategoryApi;
import com.projeto.ecommerce.dto.category.CategoryCreateDTO;
import com.projeto.ecommerce.dto.category.CategoryResponseDTO;
import com.projeto.ecommerce.dto.category.CategoryUpdateDTO;
import com.projeto.ecommerce.entity.Category;
import com.projeto.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController implements CategoryApi {

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

    @PutMapping("{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@RequestBody @Validated CategoryUpdateDTO data,
                                                              @PathVariable("id") Long id){

        var category = categoryService.updateCategory(data, id);
        return ResponseEntity.ok().body(new CategoryResponseDTO(category));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id){

        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
