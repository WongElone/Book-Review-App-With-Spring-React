package com.example.demo.dto;

import com.example.demo.model.Review;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ReviewDTOMapper implements Function<Review, ReviewDTO> {
    @Override
    public ReviewDTO apply(Review review) {
        return new ReviewDTO(
                review.getId(),
                new SimpleBook(review.getBook().getId(), review.getBook().getTitle()),
                review.getTitle(),
                review.getBody(),
                review.getRating()
        );
    }
}
