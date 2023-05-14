package com.example.demo.controller;

import com.example.demo.dto.AuthorDTO;
import com.example.demo.dto.AuthorRequest;
import com.example.demo.model.Author;
import com.example.demo.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping("/")
    public ResponseEntity<String> add(@RequestBody @Valid AuthorRequest authorRequest) {
        authorService.saveAuthor(authorRequest);
        return new ResponseEntity<>("new author saved!", HttpStatus.OK);
    }

    @GetMapping("/")
    public List<AuthorDTO> getAll() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public AuthorDTO getOne(@PathVariable Long id) {
        return authorService.getOneAuthor(id);
    }
}
