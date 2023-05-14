package com.example.demo.service;

import com.example.demo.dto.AuthorDTO;
import com.example.demo.dto.AuthorDTOMapper;
import com.example.demo.dto.AuthorRequest;
import com.example.demo.exception.AuthorService404Exception;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorDTOMapper authorDTOMapper;
    @Autowired
    private BookRepository bookRepository;
    public void saveAuthor(AuthorRequest authorRequest) {
        Author author = new Author(
                authorRequest.fullName(),
                authorRequest.bookIds().stream()
                        .map(bookId -> bookRepository
                                .findById(bookId)
                                .orElseThrow(() -> new AuthorService404Exception("Book with given Id " + bookId + " not found."))
                        )
                        .collect(Collectors.toList())
        );
        authorRepository.save(author);
    }

    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream().map(authorDTOMapper).collect(Collectors.toList());
    }

    public AuthorDTO getOneAuthor(Long authorId) {
        return authorRepository
                .findById(authorId)
                .map(authorDTOMapper)
                .orElseThrow(() -> new AuthorService404Exception("Author with give id " + authorId + " not found."));
    }
}
