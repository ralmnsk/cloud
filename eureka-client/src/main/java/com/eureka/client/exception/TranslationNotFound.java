package com.eureka.client.exception;

public class TranslationNotFound extends RuntimeException{
    public TranslationNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public TranslationNotFound(String message) {
        super(message);
    }
}
