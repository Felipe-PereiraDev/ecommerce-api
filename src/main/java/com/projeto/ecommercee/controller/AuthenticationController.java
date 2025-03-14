package com.projeto.ecommercee.controller;

import com.projeto.ecommercee.dto.LoginRequestDTO;
import com.projeto.ecommercee.dto.TokenResponse;
import com.projeto.ecommercee.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Authentication", description = "Endpoints   para autenticação de usuários.")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @Operation(
            summary = "Autenticar usuário",
            description = "Autentica um usuário e retorna um token JWT válido para acesso aos endpoints protegidos."
    )
    @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida, retorna o token JWT")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas, acesso não autorizado")
    @PostMapping("/auth")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody LoginRequestDTO data) {
        var token = authenticationService.authenticate(data);
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
