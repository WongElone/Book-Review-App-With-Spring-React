package com.example.demo.controller;

import com.example.demo.model.Author;
import com.example.demo.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping("/")
    public Author add(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }

    @GetMapping("/")
    public List<Author> getAll() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Optional<Author> getOne(@PathVariable Long id) {
        return authorService.getOneAuthor(id);
    }
}
