package com.projeto.ecommerce.controller;


import com.projeto.ecommerce.controller.docs.ProductApi;
import com.projeto.ecommerce.dto.product.ProductCreateDto;
import com.projeto.ecommerce.dto.product.ProductResponseDTO;
import com.projeto.ecommerce.dto.product.ProductUpdateDTO;
import com.projeto.ecommerce.entity.Product;
import com.projeto.ecommerce.service.CategoryService;
import com.projeto.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProductController implements ProductApi {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> listAllProducts(
            @ParameterObject
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "category", required = false) String category) {
        return ResponseEntity.ok(productService.listAllProducts(pageable, category));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDTO>> searchProductsByName(
            @ParameterObject
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
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
