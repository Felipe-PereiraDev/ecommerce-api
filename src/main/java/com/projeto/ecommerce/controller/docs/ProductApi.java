package com.projeto.ecommerce.controller.docs;

import com.projeto.ecommerce.dto.product.ProductCreateDto;
import com.projeto.ecommerce.dto.product.ProductResponseDTO;
import com.projeto.ecommerce.dto.product.ProductUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Product", description = "Endpoints para gerenciar produtos")
public interface ProductApi {
    @Operation(summary = "Listar produtos", description = "Retorna uma lista páginada de produtos.")
    ResponseEntity<Page<ProductResponseDTO>> listAllProducts(
            @ParameterObject
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "category", required = false) String category);


    @Operation(summary = "Buscar produtos pelo nome", description = "Retorna uma lista de produtos filtrados pelo nome fornecido.")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    @ApiResponse(responseCode = "400", description = "Nome não fornecido ou inválido")
    ResponseEntity<Page<ProductResponseDTO>> searchProductsByName(
            @ParameterObject
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "name", defaultValue = "")String name);

    @Operation(summary = "Criar produto", description = "Cria um novo produto.")
    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado, apenas administradores podem criar produtos")
    ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Validated ProductCreateDto productDto);

    @Operation(summary = "Atualizar produto", description = "Atualiza as informações de um produto especificado pelo ID.")
    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado, apenas administradores podem atualizar produtos")
    ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable("id") Long id,
                                                     @RequestBody ProductUpdateDTO productUpdateDTO);

    @Operation(summary = "Deletar produto", description = "Deleta um produto pelo ID")
    @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado.")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado.")
    ResponseEntity<?> deleteProduct(@PathVariable("id") Long id);
}
