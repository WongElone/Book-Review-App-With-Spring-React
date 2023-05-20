package com.example.demo.controller;

import com.example.demo.dto.AuthorRequest;
import com.example.demo.dto.AuthorResponse;
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

    @PostMapping
    public ResponseEntity<AuthorResponse> addOneAuthor(@RequestBody @Valid AuthorRequest authorRequest) {
        return new ResponseEntity<>(authorService.saveAuthor(authorRequest), HttpStatus.OK);
    }

    @GetMapping
    public List<AuthorResponse> getAllAuthors(@RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) Integer size,
                                         @RequestParam(required = false) String sort,
                                         @RequestParam(defaultValue = "false") Boolean desc) {
        if (page == null || size == null) {
            if (sort == null) {
                return authorService.getAllAuthors();
            } else {
                return authorService.getAllAuthors(sort, desc);
            }
        } else if (sort == null) {
            return authorService.getAllAuthors(page, size);
        }
        return authorService.getAllAuthors(page, size, sort, desc);
    }

    @GetMapping("/{id}")
    public AuthorResponse getOneAuthor(@PathVariable Long id) {
        return authorService.getOneAuthor(id);
    }

    @PutMapping("/{id}")
    public AuthorResponse updateOneAuthor(@PathVariable Long id, @RequestBody @Valid AuthorRequest authorRequest) {
        return authorService.updateOneAuthor(id, authorRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOneAuthor(@PathVariable Long id) {
        authorService.deleteOneAuthor(id);
    }
}
