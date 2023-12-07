package com.ivanfrias.myapi.Exceptions;

public class EmailNotUniqueException extends Exception{
    // Constructor con un mensaje personalizado
    public EmailNotUniqueException(String message) {
        super(message);
    }

    // Constructor con un mensaje y una causa (Throwable)
    public EmailNotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }
}
