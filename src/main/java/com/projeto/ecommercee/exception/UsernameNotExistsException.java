package com.projeto.ecommercee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsernameNotExistsException extends RuntimeException{
    public UsernameNotExistsException() {
        super("O username não está cadastrado no sistema");
    }
}
