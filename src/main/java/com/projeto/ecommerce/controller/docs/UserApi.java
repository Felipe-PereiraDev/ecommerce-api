package com.projeto.ecommerce.controller.docs;

import com.projeto.ecommerce.dto.user.AddressCreateDTO;
import com.projeto.ecommerce.dto.user.UserCreateDTO;
import com.projeto.ecommerce.dto.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Tag(name = "User", description = "Endpoints para gerenciar usuários")
public interface UserApi {

    @Operation(summary = "Criar um novo usuário", description = "Cadastra um novo usuário no sistema.")
    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso")
    ResponseEntity<UserResponseDTO> createUser(@RequestBody @Validated UserCreateDTO data);

    @Operation(summary = "Buscar usuário", description = "Busca um usuário pelo id.")
    ResponseEntity<UserResponseDTO> findUserById(@PathVariable("id") String id);

    @Operation(summary = "Listar usuários", description = "Retorna a lista todos os usuários do banco de dados. Apenas usuários com perfil ADMIN podem acessar este recurso")
    ResponseEntity<List<UserResponseDTO>> listAllUsers();


    @Operation(summary = "Adicionar endereço", description = "Adiciona um novo endereço ao usuário especificado pelo ID. O endereço deve ser enviado no corpo da requisição.")
    @ApiResponse(responseCode = "200", description = "Endereço cadastrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ResponseEntity<?> addAddress(@PathVariable("userId") String userId,
                                        @RequestBody AddressCreateDTO data);
}

