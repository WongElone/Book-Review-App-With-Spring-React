package com.example.demo.dto;


import com.example.demo.model.Author;
import com.example.demo.model.Review;

import java.util.List;

public record BookDTO (
    Long id,
    String title,
    String description,
    List<SimpleAuthor> authors,
    List<Review> reviews
) {}

