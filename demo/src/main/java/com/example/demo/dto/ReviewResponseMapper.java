package com.example.demo.dto;

import com.example.demo.model.Review;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ReviewResponseMapper implements Function<Review, ReviewResponse> {
    @Override
    public ReviewResponse apply(Review review) {
        return new ReviewResponse(
                review.getId(),
                new SimpleBook(review.getBook().getId(), review.getBook().getTitle()),
                review.getTitle(),
                review.getBody(),
                review.getRating(),
                review.getCreatedAt()
        );
    }
}
