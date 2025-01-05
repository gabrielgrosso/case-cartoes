package com.utai.cartoes.exceptions;

public class BadRequestException extends Exception{
    public BadRequestException(){
        super("Requisição incorreta. Por favor, revise e tente novamente.");
    }

    public BadRequestException(String mensagem){
        super(mensagem);
    }

    public BadRequestException(String mensagem, Throwable causa){
        super(mensagem, causa);
    }
}
