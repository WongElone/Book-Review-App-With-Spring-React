package com.example.demo.dto;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookDTOMapper implements Function<Book, BookDTO> {
    @Override
    public BookDTO apply(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getDescription(),
                book.getAuthors().stream()
                        .map(author -> new SimpleAuthor(author.getId(), author.getFullName()))
                        .collect(Collectors.toList()),
                book.getReviews()
        );
    }
}
