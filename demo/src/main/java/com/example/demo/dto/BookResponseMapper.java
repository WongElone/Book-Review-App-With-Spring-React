package com.example.demo.dto;

import com.example.demo.model.Book;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookResponseMapper implements Function<Book, BookResponse> {
    @Override
    public BookResponse apply(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getDescription(),
                book.getAuthors().stream()
                        .map(author -> new SimpleAuthor(author.getId(), author.getFullName()))
                        .collect(Collectors.toList()),
                book.getReviews().stream()
                        .map(review -> new SimpleReview(review.getId(), review.getTitle(), review.getRating()))
                        .collect(Collectors.toList()),
                book.getCoverImageRelativeUri(),
                book.getFirstPublicationYear(),
                book.getUpdatedAt()
        );
    }
}
