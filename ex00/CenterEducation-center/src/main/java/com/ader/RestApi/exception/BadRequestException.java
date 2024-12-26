package com.ader.RestApi.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
        System.out.println("BadRequestException: " + message);
    }
}
