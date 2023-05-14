package com.example.demo.dto;

import com.example.demo.model.Author;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AuthorDTOMapper implements Function<Author, AuthorDTO> {
    @Override
    public AuthorDTO apply(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getFullName(),
                author.getBooks().stream().map(book -> new SimpleBook(
                        book.getId(),
                        book.getTitle()
                )).collect(Collectors.toList())
        );
    }
}
