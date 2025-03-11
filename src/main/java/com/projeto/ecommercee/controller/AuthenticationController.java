package com.projeto.ecommercee.controller;

import com.projeto.ecommercee.dto.LoginRequestDTO;
import com.projeto.ecommercee.dto.TokenResponse;
import com.projeto.ecommercee.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/auth")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody LoginRequestDTO data) {
        var token = authenticationService.authenticate(data);
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
