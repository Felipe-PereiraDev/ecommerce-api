package com.projeto.ecommerce.controller.docs;

import com.projeto.ecommerce.dto.category.CategoryCreateDTO;
import com.projeto.ecommerce.dto.category.CategoryResponseDTO;
import com.projeto.ecommerce.dto.category.CategoryUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category", description = "Endpoints para gerenciar categorias")
public interface CategoryApi {

    @Operation(summary = "Listar Categorias", description = "Retorna uma Lista de todas as categorias do banco de dados")
    ResponseEntity<List<CategoryResponseDTO>> listCategory();

    @Operation(summary = "Criar uma nova categoria", description = "Cadastra uma nova categoria no sistema.")
    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso")
    ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody @Validated CategoryCreateDTO data);

    @Operation(
            summary = "Atualizar uma categoria existente",
            description = "Atualiza os dados de uma categoria existente identificada pelo ID. " +
                    "Os campos fornecidos no corpo da requisição serão sobrescritos na categoria correspondente. " +
                    "Retorna o recurso atualizado em caso de sucesso."
    )
    @ApiResponse(responseCode = "200", description = "Recurso atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    ResponseEntity<CategoryResponseDTO> updateCategory(@RequestBody @Validated CategoryUpdateDTO data,
                                                              @PathVariable("id") Long id);

    @Operation(
            summary = "Excluir uma categoria existente",
            description = "Remove uma categoria existente identificada pelo ID. " +
                    "A operação só pode ser realizada por usuários com perfil de administrador. " +
                    "Não retorna conteúdo em caso de sucesso."
    )
    @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado. Apenas administradores podem deletar categorias.")
    @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id);

}
