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

import java.util.ArrayList;
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
    public AuthorDTO saveAuthor(AuthorRequest authorRequest) {
        List<Long> bookIds = (authorRequest.bookIds() != null) ? authorRequest.bookIds() : new ArrayList<>();
        Author author = new Author(
                authorRequest.fullName(),
                bookIds.stream()
                        .map(bookId -> bookRepository
                                .findById(bookId)
                                .orElseThrow(() -> new AuthorService404Exception("Book with given Id " + bookId + " not found."))
                        )
                        .toList()
        );
        return authorDTOMapper.apply(authorRepository.save(author));
    }

    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream().map(authorDTOMapper).toList();
    }

    public AuthorDTO getOneAuthor(Long authorId) {
        return authorRepository
                .findById(authorId)
                .map(authorDTOMapper)
                .orElseThrow(() -> new AuthorService404Exception("Author with give id " + authorId + " not found."));
    }

    public AuthorDTO updateOneAuthor(Long authorId, AuthorRequest authorRequest) {
        Author author = authorRepository
                .findById(authorId)
                .orElseThrow(() -> new AuthorService404Exception("Author with give id " + authorId + " not found."));

        author.setFullName(authorRequest.fullName());
        List<Long> bookIds = (authorRequest.bookIds() != null) ? authorRequest.bookIds() : new ArrayList<>();
        author.setBooks(bookIds.stream()
                .map(bookId -> bookRepository
                        .findById(bookId)
                        .orElseThrow(() -> new AuthorService404Exception("Book with given Id " + bookId + " not found.")))
                .toList()
        );
        return authorDTOMapper.apply(authorRepository.save(author));
    }

    public void deleteOneAuthor(Long authorId) {
        authorRepository.findById(authorId).orElseThrow(() -> new AuthorService404Exception("Author with give id " + authorId + " not found."));
        authorRepository.deleteById(authorId);
    }
}
