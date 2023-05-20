package com.example.demo.dto;


import com.example.demo.model.Review;

import java.util.List;

public record BookResponse(
    Long id,
    String title,
    String description,
    List<SimpleAuthor> authors,
    List<Review> reviews,
    String coverImageRelativeUri
) {}

