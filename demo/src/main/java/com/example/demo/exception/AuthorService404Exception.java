package com.example.demo.exception;

public class AuthorService404Exception extends RuntimeException {
    public AuthorService404Exception(String message) {
        super(message);
    }
}
