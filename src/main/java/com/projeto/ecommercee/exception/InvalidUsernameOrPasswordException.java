package com.projeto.ecommercee.exception;

public class InvalidUsernameOrPasswordException extends RuntimeException{
    public InvalidUsernameOrPasswordException() {
        super("Username ou Senha inválida.");
    }
}
