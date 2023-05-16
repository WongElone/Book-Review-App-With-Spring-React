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

    public List<ReviewDTO> getAllReviews(Integer page, Integer size, String sort, Boolean desc) {
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
            reviews = reviewRepository.findAll(pageable).toList();
        }
        return reviews.stream().map(reviewDTOMapper).toList();
    }

    public List<ReviewDTO> getAllReviews(Long bookId, Integer page, Integer size, String sort, Boolean desc) {
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
            reviews = reviewRepository.findReviewsByBookId(bookId, pageable).toList();
        }
        return reviews.stream().map(reviewDTOMapper).toList();
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

    public void deleteOneReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
