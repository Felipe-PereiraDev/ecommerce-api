package com.projeto.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException() {
        super("Acesso negado. Você não tem permissão para acessar este recurso.");
    }
}
