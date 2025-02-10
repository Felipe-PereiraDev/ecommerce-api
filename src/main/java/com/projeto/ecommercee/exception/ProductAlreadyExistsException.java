package com.projeto.ecommercee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductAlreadyExistsException extends RuntimeException{
    public ProductAlreadyExistsException() {
        super("O produto já foi cadastrado, tente outro nome.");
    }
}
