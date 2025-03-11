package com.projeto.ecommercee.service;

import com.projeto.ecommercee.dto.LoginRequestDTO;
import com.projeto.ecommercee.exception.AccessDeniedException;
import com.projeto.ecommercee.exception.InvalidUsernameOrPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public String authenticate(LoginRequestDTO login) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            login.username(),
                            login.password()
                    )
            );
            return jwtService.generateToken(authentication);
        } catch (BadCredentialsException e) {
            throw new InvalidUsernameOrPasswordException();
        }
    }

    public boolean hasAccessPermission(String id) {
        Object userPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(userPrincipal instanceof Jwt jwtToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não autenticado ou token inválido.");
        }
        String username = jwtToken.getSubject();
        var userAuth = userService.findByUsername(username);
        boolean isAdmin = userAuth.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));

        boolean noPermission = !isAdmin && !userAuth.getId().equals(UUID.fromString(id));
        if (noPermission) throw new AccessDeniedException();

        return true;
    }
}
