package com.example.demo.service;

import com.example.demo.dto.ReviewResponse;
import com.example.demo.dto.ReviewResponseMapper;
import com.example.demo.dto.ReviewRequest;
import com.example.demo.exception.ReviewService404Exception;
import com.example.demo.model.Book;
import com.example.demo.model.Review;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
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

    public List<ReviewResponse> getAllReviews(Integer page, Integer size, String sort, Boolean desc) {
        List<Review> reviews;
        if (page == null || size == null) {
            if (sort == null) {
                reviews = reviewRepository.findAll();
            } else {
                reviews = reviewRepository.findAll(
                        Sort.by(desc ? Sort.Direction.DESC : Sort.Direction.ASC, sort)
                );
            }
        } else {
            Pageable pageable = (sort == null)
                    ? PageRequest.of(page, size)
                    : PageRequest.of(page, size, Sort.by(desc ? Sort.Direction.DESC : Sort.Direction.ASC, sort));
            reviews = reviewRepository.findAll(pageable).stream().collect(Collectors.toList());
        }
        return reviews.stream().map(reviewResponseMapper).collect(Collectors.toList());
    }

    public List<ReviewResponse> getAllReviews(Long bookId, Integer page, Integer size, String sort, Boolean desc) {
        if (!bookRepository.existsById(bookId)) throw new ReviewService404Exception("Book with given Id " + bookId + " not found.");

        List<Review> reviews;
        if (page == null || size == null) {
            if (sort == null) {
                reviews = reviewRepository.findReviewsByBookId(bookId);
            } else {
                reviews = reviewRepository.findReviewsByBookId(bookId,
                        Sort.by(desc ? Sort.Direction.DESC : Sort.Direction.ASC, sort)
                );
            }
        } else {
            Pageable pageable = (sort == null)
                    ? PageRequest.of(page, size)
                    : PageRequest.of(page, size, Sort.by(desc ? Sort.Direction.DESC : Sort.Direction.ASC, sort));
            reviews = reviewRepository.findReviewsByBookId(bookId, pageable).stream().collect(Collectors.toList());
        }
        return reviews.stream().map(reviewResponseMapper).toList();
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
