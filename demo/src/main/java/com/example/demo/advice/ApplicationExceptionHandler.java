package com.example.demo.advice;

import com.example.demo.exception.AuthorService404Exception;
import com.example.demo.exception.BookService404Exception;
import com.example.demo.exception.ReviewService404Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> manveHandler(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return errorMap;
    }
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> hmnreHandler(HttpMessageNotReadableException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookService404Exception.class)
    public Map<String, String> bookServiceExceptionHandler(BookService404Exception ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(AuthorService404Exception.class)
    public Map<String, String> authorService404ExceptionHandler(AuthorService404Exception ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ReviewService404Exception.class)
    public Map<String, String> reviewService404ExceptionHandler(ReviewService404Exception ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Map<String, List<String>> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        Map<String, List<String>> errorMap = new HashMap<>();
        List<String> errors = Collections.singletonList(ex.getMessage());
        errorMap.put("errorMessages", errors);
        return errorMap;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, List<String>> handleGeneralExceptions(Exception ex) {
        Map<String, List<String>> errorMap = new HashMap<>();
        List<String> errors = Collections.singletonList(ex.getMessage());
        errorMap.put("errorMessages", errors);
        return errorMap;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public Map<String, List<String>> handleRuntimeExceptions(RuntimeException ex) {
        Map<String, List<String>> errorMap = new HashMap<>();
        List<String> errors = Collections.singletonList(ex.getMessage());
        errorMap.put("errorMessages", errors);
        return errorMap;
    }
}
