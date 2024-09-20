package it.gestione.tavoli.GestioneTavoli.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}