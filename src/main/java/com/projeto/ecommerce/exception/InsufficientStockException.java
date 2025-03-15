package com.projeto.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InsufficientStockException extends RuntimeException{
    public InsufficientStockException(String produto) {
        super("Estoque insuficiente para o produto solicitado : " + produto);
    }
}
