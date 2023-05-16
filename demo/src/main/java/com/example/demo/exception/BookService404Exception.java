package com.example.demo.exception;

public class BookService404Exception extends RuntimeException {
    public BookService404Exception(String message) {
        super(message);
    }
}
