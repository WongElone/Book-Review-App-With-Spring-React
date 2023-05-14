package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.BookDTOMapper;
import com.example.demo.dto.BookRequest;
import com.example.demo.exception.BookServiceException;
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
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookDTOMapper bookDTOMapper;
    public Book addOneBook(BookRequest bookRequest) {
        System.out.println("book request: " + bookRequest);
        // for each author in request body, find author by id from db or create new author
        Book book = new Book(
                bookRequest.title(),
                bookRequest.description(),
                bookRequest.authorIds().stream().map(authorId -> {
                    // get author by id from db
                    Optional<Author> authorEntity = authorRepository.findById(authorId);
                    if (authorEntity.isPresent()) {
                        return authorEntity.get();
                    }
                    // throw exception when author not found
                    throw new BookServiceException("Author with given id " + authorId + " not found.");
                }).collect(Collectors.toList()),
                bookRequest.reviews());
//        List<Author> authors = book.getAuthors().stream().map(author -> {
//            Long authorId = author.getId();
//
//            if (authorId == null) return author;
//
//            Optional<Author> authorEntity = authorRepository.findById(authorId);
//            if (authorEntity.isPresent()) {
//                return authorEntity.get();
//            }
//            throw new IllegalStateException("Author with given id " + authorId + " not found.");
//        }).collect(Collectors.toList());
//        book.setAuthors(authors);
        return bookRepository.save(book);
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookDTOMapper)
                .collect(Collectors.toList());
    }

    public BookDTO getOneBook(Long bookId) throws BookServiceException {
        return bookRepository.findById(bookId)
                .map(bookDTOMapper)
                .orElseThrow(() -> new BookServiceException("Book with given id " + bookId + " not found."));
    }

    public Book updateOneBook(Long bookId, Book newBook) {
        return bookRepository.findById(bookId)
                .map((book) -> {
                    List<Author> authors = newBook.getAuthors();

                    List<Author> newAuthors = authors.stream().map(author -> {
                        Long authorId = author.getId();

                        if (authorId == null) return author;

                        Optional<Author> authorEntity = authorRepository.findById(authorId);
                        if (authorEntity.isPresent()) {
                            return authorEntity.get();
                        }
                        throw new IllegalStateException("Author with given id " + authorId + " not found.");
                    }).collect(Collectors.toList());
                    book.setAuthors(newAuthors);

                    book.setTitle(newBook.getTitle());
                    book.setDescription(newBook.getDescription());
                    book.setReviews(newBook.getReviews());
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new IllegalStateException("student with id " + bookId + " does not exist"));

    }

    public void deleteOneBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

}
