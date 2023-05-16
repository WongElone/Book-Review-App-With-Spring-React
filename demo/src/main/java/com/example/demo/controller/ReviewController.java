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
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public List<ReviewDTO> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{reviewId}")
    public ReviewDTO getOneReview(@PathVariable Long reviewId) {
        return reviewService.getOneReview(reviewId);
    }

    @PutMapping("/{reviewId}")
    public ReviewDTO updateOneReview(@PathVariable Long reviewId, @RequestBody @Valid ReviewRequest reviewRequest) {
        return reviewService.updateOneReview(reviewId, reviewRequest);
    }

    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOne(@PathVariable Long reviewId) {
        reviewService.deleteOneReview(reviewId);
    }
}
