package com.projeto.ecommerce.exception;

public class InvalidUsernameOrPasswordException extends RuntimeException{
    public InvalidUsernameOrPasswordException() {
        super("Username ou Senha inválida.");
    }
}
