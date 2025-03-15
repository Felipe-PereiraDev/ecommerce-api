package com.projeto.ecommerce.controller;

import com.projeto.ecommerce.dto.user.AddressCreateDTO;
import com.projeto.ecommerce.dto.user.UserCreateDTO;
import com.projeto.ecommerce.dto.user.UserResponseDTO;
import com.projeto.ecommerce.entity.User;
import com.projeto.ecommerce.service.AuthenticationService;
import com.projeto.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User", description = "Endpoints para gerenciar usuários")
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Criar um novo usuário", description = "Cadastra um novo usuário no sistema.",
            responses = @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso"))
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Validated UserCreateDTO data) {
        var createdUser = userService.createUser(data);
        String url = "/user/" + createdUser.getId().toString();
        return ResponseEntity.created(URI.create(url)).body(new UserResponseDTO(createdUser));
    }

    @Operation(summary = "Buscar usuário", description = "Busca um usuário pelo id.")
    @GetMapping("{id}")
    @PreAuthorize("@authenticationService.hasAccessPermission(#id)")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable("id") String id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(new UserResponseDTO(user));

    }

    @Operation(summary = "Listar usuários", description = "Retorna a lista todos os usuários do banco de dados. Apenas usuários com perfil ADMIN podem acessar este recurso")
    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> listAllUsers() {
        List<UserResponseDTO> users  = userService.findAll().stream()
                .map(UserResponseDTO::new).sorted(Comparator.comparing(UserResponseDTO::username)).toList();
        return ResponseEntity.ok(users);
    }


    @Operation(summary = "Adicionar endereço", description = "Adiciona um novo endereço ao usuário especificado pelo ID. O endereço deve ser enviado no corpo da requisição.",
            responses = {
            @ApiResponse(responseCode = "200", description = "Endereço cadastrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping("/{userId}/address")
    @PreAuthorize("@authenticationService.hasAccessPermission(#id)")
    public ResponseEntity<?> addAddress(@PathVariable("userId") String userId,
                                        @RequestBody AddressCreateDTO data) {
        userService.addAddress(userId, data);
        return ResponseEntity.ok().build();
    }
}
