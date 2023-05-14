package com.example.demo.controller;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.BookRequest;
import com.example.demo.exception.BookServiceException;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/")
    public ResponseEntity<String> addOne(@RequestBody @Valid BookRequest book) {
        bookService.addOneBook(book);
        return new ResponseEntity<>("new book saved!", HttpStatus.CREATED);
    }

    @GetMapping("/")
    public List<BookDTO> getAll() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDTO getOne(@PathVariable Long id) throws BookServiceException {
        return bookService.getOneBook(id);
    }

    @PutMapping("/{id}")
    public Book updateOne(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateOneBook(id, book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteOne(@PathVariable Long id) {
        bookService.deleteOneBook(id);
    }
}
