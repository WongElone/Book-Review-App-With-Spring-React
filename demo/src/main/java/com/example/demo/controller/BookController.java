package com.example.demo.controller;

import com.example.demo.dto.BookResponse;
import com.example.demo.dto.BookRequest;
import com.example.demo.dto.ReviewResponse;
import com.example.demo.dto.ReviewRequest;
import com.example.demo.exception.BookService404Exception;
import com.example.demo.service.BookService;
import com.example.demo.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private ReviewService reviewService;

    // books
    @PostMapping
    public ResponseEntity<BookResponse> addOne(@RequestBody @Valid BookRequest book) {
        return new ResponseEntity<>(bookService.addOneBook(book), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<BookResponse> getAllBooks(@RequestParam(defaultValue = "0") Integer page,
                                          @RequestParam(defaultValue = "6") Integer size,
                                          @RequestParam(required = false) String sort,
                                          @RequestParam(defaultValue = "false") Boolean desc) {
        return bookService.getAllBooks(page, size, sort, desc);
    }

    @GetMapping("/{id}")
    public BookResponse getOneBook(@PathVariable Long id) throws BookService404Exception {
        return bookService.getOneBook(id);
    }

    @PutMapping("/{id}")
    public BookResponse updateOneBook(@PathVariable Long id, @RequestBody @Valid BookRequest bookRequest) {
        return bookService.updateOneBook(id, bookRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteOneBook(@PathVariable Long id) {
        bookService.deleteOneBook(id);
    }
    // end of books

    // reviews of books
    @PostMapping("/{bookId}/reviews")
    public ResponseEntity<ReviewResponse> addOneReviewToBook(@RequestBody @Valid ReviewRequest reviewRequest, @PathVariable Long bookId) {
        return new ResponseEntity<>(reviewService.addOneReview(reviewRequest, bookId), HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}/reviews")
    public Page<ReviewResponse> getAllReviewsOfBook(@PathVariable Long bookId,
                                                    @RequestParam(defaultValue = "0") Integer page,
                                                    @RequestParam(defaultValue = "6") Integer size,
                                                    @RequestParam(required = false) String sort,
                                                    @RequestParam(defaultValue = "false") Boolean desc) {
        return reviewService.getAllReviews(bookId, page, size, sort, desc);
    }

    @GetMapping("/{bookId}/reviews/{reviewId}")
    public ReviewResponse getOneReviewOfBook(@PathVariable Long bookId, @PathVariable Long reviewId) {
        return reviewService.getOneReview(bookId, reviewId);
    }
    // end of reviews of books
}
