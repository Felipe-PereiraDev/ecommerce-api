package com.projeto.ecommercee.controller;


import com.projeto.ecommercee.dto.product.CategoryResponseDTO;
import com.projeto.ecommercee.dto.product.ProductCreateDto;
import com.projeto.ecommercee.dto.product.ProductResponseDTO;
import com.projeto.ecommercee.dto.product.ProductUpdateDTO;
import com.projeto.ecommercee.entity.Category;
import com.projeto.ecommercee.entity.Product;
import com.projeto.ecommercee.service.CategoryService;
import com.projeto.ecommercee.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> listProducts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                                                 @RequestParam(value = "sort", defaultValue = "name") String sort,
                                                                 @RequestParam(value = "category", required = false) String category) {
        return ResponseEntity.ok(productService.getProducts(page, size, sort, category));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody @Validated ProductCreateDto productDto) {
        Product createdProduct = productService.createProduct(productDto);
        String location = "/products/" + createdProduct.getId();
        return ResponseEntity.created(URI.create(location))
                .body(new ProductResponseDTO(createdProduct));
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
