package com.example.demo.exception;

public class BookServiceException extends RuntimeException {
    public BookServiceException(String message) {
        super(message);
    }
}
