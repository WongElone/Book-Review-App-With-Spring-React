package com.example.demo.controller;

import com.example.demo.dto.ReviewDTO;
import com.example.demo.dto.ReviewRequest;
import com.example.demo.model.Review;
import com.example.demo.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books/{bookId}/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/")
    public ResponseEntity<String> addOne(@RequestBody @Valid ReviewRequest reviewRequest, @PathVariable Long bookId) {
        reviewService.addOneReview(reviewRequest, bookId);
        return new ResponseEntity<>("new review added!", HttpStatus.CREATED);
    }

    @GetMapping("/")
    public List<ReviewDTO> getAll(@PathVariable Long bookId) {
        return reviewService.getAllReviews(bookId);
    }

    @GetMapping("/{reviewId}")
    public ReviewDTO getOne(@PathVariable Long bookId, @PathVariable Long reviewId) {
        return reviewService.getOneReview(bookId, reviewId);
    }

}
