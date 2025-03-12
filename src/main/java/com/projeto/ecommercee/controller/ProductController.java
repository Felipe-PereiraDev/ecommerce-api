package com.projeto.ecommercee.controller;


import com.projeto.ecommercee.dto.product.ProductCreateDto;
import com.projeto.ecommercee.dto.product.ProductResponseDTO;
import com.projeto.ecommercee.dto.product.ProductUpdateDTO;
import com.projeto.ecommercee.entity.Product;
import com.projeto.ecommercee.service.CategoryService;
import com.projeto.ecommercee.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> listAllProducts(@PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
                                                                    @RequestParam(value = "category", required = false) String category) {
        return ResponseEntity.ok(productService.listAllProducts(pageable, category));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDTO>> searchProductsByName(@PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
                                                                         @RequestParam(value = "name", defaultValue = "")String name) {
        if (name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(productService.searchProductsByName(pageable, name));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Validated ProductCreateDto productDto) {
        Product createdProduct = productService.createProduct(productDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdProduct.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProductResponseDTO(createdProduct));
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable("id") Long id,
                                                            @RequestBody ProductUpdateDTO productUpdateDTO) {
        var product = productService.updateProduct(id, productUpdateDTO);
        return ResponseEntity.ok(new ProductResponseDTO(product));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id){
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


 }
