package com.projeto.ecommercee.controller;


import com.projeto.ecommercee.dto.product.ProductCreateDto;
import com.projeto.ecommercee.dto.product.ProductResponseDTO;
import com.projeto.ecommercee.dto.product.ProductUpdateDTO;
import com.projeto.ecommercee.entity.Product;
import com.projeto.ecommercee.service.CategoryService;
import com.projeto.ecommercee.service.ProductService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
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
@Tag(name = "Product", description = "Endpoints para gerenciar produtos")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Operation(summary = "Listar produtos", description = "Retorna uma lista páginada de produtos.")
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> listAllProducts(
            @ParameterObject
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "category", required = false) String category) {
        return ResponseEntity.ok(productService.listAllProducts(pageable, category));
    }


    @Operation(summary = "Buscar produtos pelo nome", description = "Retorna uma lista de produtos filtrados pelo nome fornecido.")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    @ApiResponse(responseCode = "400", description = "Nome não fornecido ou inválido")
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

    @Operation(summary = "Criar produto", description = "Cria um novo produto.")
    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado, apenas administradores podem criar produtos")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Validated ProductCreateDto productDto) {
        Product createdProduct = productService.createProduct(productDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdProduct.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProductResponseDTO(createdProduct));
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza as informações de um produto especificado pelo ID.")
    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado, apenas administradores podem atualizar produtos")
    @PutMapping("{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable("id") Long id,
                                                            @RequestBody ProductUpdateDTO productUpdateDTO) {
        var product = productService.updateProduct(id, productUpdateDTO);
        return ResponseEntity.ok(new ProductResponseDTO(product));
    }

    @Operation(summary = "Deletar produto", description = "Deleta um produto pelo ID")
    @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado.")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado.")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id){
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


 }
