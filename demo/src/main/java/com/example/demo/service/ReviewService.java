package com.example.demo.service;

import com.example.demo.dto.ReviewResponse;
import com.example.demo.dto.ReviewResponseMapper;
import com.example.demo.dto.ReviewRequest;
import com.example.demo.exception.ReviewService404Exception;
import com.example.demo.model.Book;
import com.example.demo.model.Review;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.ReviewRepository;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Service
@Validated
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReviewResponseMapper reviewResponseMapper;

    public ReviewResponse addOneReview(ReviewRequest reviewRequest, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ReviewService404Exception("Book with given Id " + bookId + " not found."));
        Review review = new Review(
                book,
                reviewRequest.title(),
                reviewRequest.body(),
                reviewRequest.rating()
        );
        return reviewResponseMapper.apply(reviewRepository.save(review));
    }

    public Page<ReviewResponse> getAllReviews(@Min(0) Integer page,
                                              @Max(6) @Min(1) Integer size,
                                              String sort,
                                              Boolean desc) {
        Pageable pageable = (sort == null)
                ? PageRequest.of(page, size)
                : PageRequest.of(page, size, Sort.by(desc ? Sort.Direction.DESC : Sort.Direction.ASC, sort));
        return reviewRepository.findAll(pageable).map(reviewResponseMapper);
    }

    public Page<ReviewResponse> getAllReviews(Long bookId,
                                              @Min(0) Integer page,
                                              @Max(6) @Min(1) Integer size,
                                              String sort,
                                              Boolean desc) {
        if (!bookRepository.existsById(bookId)) throw new ReviewService404Exception("Book with given Id " + bookId + " not found.");

        Pageable pageable = (sort == null)
                ? PageRequest.of(page, size)
                : PageRequest.of(page, size, Sort.by(desc ? Sort.Direction.DESC : Sort.Direction.ASC, sort));
        return reviewRepository.findReviewsByBookId(bookId, pageable).map(reviewResponseMapper);
    }

    public ReviewResponse getOneReview(Long reviewId) {
        return reviewRepository.findById(reviewId).map(reviewResponseMapper)
                .orElseThrow(() -> new ReviewService404Exception("Review with given Id " + reviewId + " not found."));
    }

    public ReviewResponse getOneReview(Long bookId, Long reviewId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ReviewService404Exception("Book with given Id " + bookId + " not found."));
        return book.getReviews().stream().filter(review -> Objects.equals(review.getId(), reviewId))
                .findFirst()
                .map(reviewResponseMapper)
                .orElseThrow(() -> new ReviewService404Exception("Review with given Id " + reviewId + " not found in book with Id " + bookId + "."));
    }

    public ReviewResponse updateOneReview(Long reviewId, ReviewRequest reviewRequest) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewService404Exception("Review with given Id " + reviewId + " not found."));

        review.setTitle(reviewRequest.title());
        review.setBody(reviewRequest.body());
        review.setRating(reviewRequest.rating());
        return reviewResponseMapper.apply(reviewRepository.save(review));
    }

    public void deleteOneReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
