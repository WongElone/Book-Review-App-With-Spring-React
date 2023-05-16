package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.BookDTOMapper;
import com.example.demo.dto.BookRequest;
import com.example.demo.exception.AuthorService404Exception;
import com.example.demo.exception.BookService404Exception;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookDTOMapper bookDTOMapper;
    public BookDTO addOneBook(BookRequest bookRequest) {
        List<Long> authorIds = (bookRequest.authorIds() != null) ? bookRequest.authorIds() : new ArrayList<>();
        Book book = new Book(
                bookRequest.title(),
                bookRequest.description(),
                authorIds.stream().map(authorId -> authorRepository
                            .findById(authorId)
                            .orElseThrow(() -> new BookService404Exception("Author with given id " + authorId + " not found."))
                ).toList()
        );
        return bookDTOMapper.apply(bookRepository.save(book));
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookDTOMapper)
                .toList();
    }

    public List<BookDTO> getAllBooks(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findAll(pageable)
                .stream()
                .map(bookDTOMapper)
                .toList();
    }

    public List<BookDTO> getAllBooks(String sort, Boolean desc) {
        Sort sortable = Sort.by(desc ? Sort.Direction.DESC : Sort.Direction.ASC, sort);
        return bookRepository.findAll(sortable)
                .stream()
                .map(bookDTOMapper)
                .toList();
    }

    public List<BookDTO> getAllBooks(Integer page, Integer size, String sort, Boolean desc) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(desc ? Sort.Direction.DESC : Sort.Direction.ASC, sort));
        return bookRepository.findAll()
                .stream()
                .map(bookDTOMapper)
                .toList();
    }
    public BookDTO getOneBook(Long bookId) throws BookService404Exception {
        return bookRepository.findById(bookId)
                .map(bookDTOMapper)
                .orElseThrow(() -> new BookService404Exception("Book with given id " + bookId + " not found."));
    }

    public BookDTO updateOneBook(Long bookId, BookRequest bookRequest) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookService404Exception("Book with given id " + bookId + " not found."))
                ;
        List<Long> authorIds = (bookRequest.authorIds() != null) ? bookRequest.authorIds() : new ArrayList<>();
        List<Author> authors = authorIds.stream().map(authorId -> {
            return authorRepository.findById(authorId)
                    .orElseThrow(() -> new AuthorService404Exception("Author with given id " + authorId + " not found."));
        }).toList();

        book.setTitle(bookRequest.title());
        book.setDescription(bookRequest.description());
        book.setAuthors(authors);
        return bookDTOMapper.apply(bookRepository.save(book));
    }

    public void deleteOneBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

}
