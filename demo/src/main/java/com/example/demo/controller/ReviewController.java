package com.example.demo.controller;

import com.example.demo.dto.ReviewResponse;
import com.example.demo.dto.ReviewRequest;
import com.example.demo.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public List<ReviewResponse> getAllReviews(@RequestParam(required = false) Integer page,
                                              @RequestParam(required = false) Integer size,
                                              @RequestParam(required = false) String sort,
                                              @RequestParam(defaultValue = "false") Boolean desc) {
        return reviewService.getAllReviews(page, size, sort, desc);
    }

    @GetMapping("/{reviewId}")
    public ReviewResponse getOneReview(@PathVariable Long reviewId) {
        return reviewService.getOneReview(reviewId);
    }

    @PutMapping("/{reviewId}")
    public ReviewResponse updateOneReview(@PathVariable Long reviewId, @RequestBody @Valid ReviewRequest reviewRequest) {
        return reviewService.updateOneReview(reviewId, reviewRequest);
    }

    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOne(@PathVariable Long reviewId) {
        reviewService.deleteOneReview(reviewId);
    }
}
