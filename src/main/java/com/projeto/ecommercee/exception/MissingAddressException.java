package com.projeto.ecommercee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingAddressException extends RuntimeException{
    public MissingAddressException() {
        super("Por favor, adicione um endereço antes de fazer um pedido!");
    }
}
