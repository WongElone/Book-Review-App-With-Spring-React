package com.example.demo.exception;

public class ReviewService404Exception extends RuntimeException {
    public ReviewService404Exception(String message) {
        super(message);
    }
}
