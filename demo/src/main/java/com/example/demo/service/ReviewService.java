package com.example.demo.service;

import com.example.demo.dto.ReviewDTO;
import com.example.demo.dto.ReviewDTOMapper;
import com.example.demo.dto.ReviewRequest;
import com.example.demo.exception.ReviewService404Exception;
import com.example.demo.model.Book;
import com.example.demo.model.Review;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ReviewDTOMapper reviewDTOMapper;

    public ReviewDTO addOneReview(ReviewRequest reviewRequest, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ReviewService404Exception("Book with given Id " + bookId + " not found."));
        Review review = new Review(
                book,
                reviewRequest.title(),
                reviewRequest.body(),
                reviewRequest.rating()
        );
        return reviewDTOMapper.apply(reviewRepository.save(review));
    }

    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream().map(reviewDTOMapper).collect(Collectors.toList());
    }

    public List<ReviewDTO> getAllReviews(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ReviewService404Exception("Book with given Id " + bookId + " not found."));
        return book.getReviews()
                .stream()
                .map(reviewDTOMapper)
                .toList();
    }

    public ReviewDTO getOneReview(Long reviewId) {
        return reviewRepository.findById(reviewId).map(reviewDTOMapper)
                .orElseThrow(() -> new ReviewService404Exception("Review with given Id " + reviewId + " not found."));
    }

    public ReviewDTO getOneReview(Long bookId, Long reviewId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ReviewService404Exception("Book with given Id " + bookId + " not found."));
        return book.getReviews().stream().filter(review -> Objects.equals(review.getId(), reviewId))
                .findFirst()
                .map(reviewDTOMapper)
                .orElseThrow(() -> new ReviewService404Exception("Review with given Id " + reviewId + " not found in book with Id " + bookId + "."));
    }

    public ReviewDTO updateOneReview(Long reviewId, ReviewRequest reviewRequest) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewService404Exception("Review with given Id " + reviewId + " not found."));

        review.setTitle(reviewRequest.title());
        review.setBody(reviewRequest.body());
        review.setRating(reviewRequest.rating());
        return reviewDTOMapper.apply(reviewRepository.save(review));
    }

    public ReviewDTO updateOneReview(Long bookId, Long reviewId, ReviewRequest reviewRequest) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ReviewService404Exception("Book with given Id " + bookId + " not found."));
        Review review = book.getReviews().stream()
                .filter(r -> Objects.equals(r.getId(), reviewId))
                .findFirst()
                .orElseThrow(() -> new ReviewService404Exception("Review with given Id " + reviewId + " not found in book with Id " + bookId + "."));
        review.setTitle(reviewRequest.title());
        review.setBody(reviewRequest.body());
        review.setRating(reviewRequest.rating());
        return reviewDTOMapper.apply(reviewRepository.save(review));
    }

    public void deleteOneReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
