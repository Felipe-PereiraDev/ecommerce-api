package com.projeto.ecommercee.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(Long id) {
        super("Pedido com o id " + id + " não encontrado.");
    }
}
