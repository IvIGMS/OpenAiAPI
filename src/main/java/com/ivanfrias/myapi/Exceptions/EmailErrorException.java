package com.ivanfrias.myapi.Exceptions;

public class EmailErrorException extends Exception {

    // Constructor con un mensaje personalizado
    public EmailErrorException(String message) {
        super(message);
    }

    // Constructor con un mensaje y una causa (Throwable)
    public EmailErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}