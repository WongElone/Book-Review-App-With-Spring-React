package com.example.demo.dto;



import java.time.Instant;
import java.util.List;

public record BookResponse(
    Long id,
    String title,
    String description,
    List<SimpleAuthor> authors,
    List<SimpleReview> reviews,
    String coverImageRelativeUri,
    Integer firstPublicationYear,
    Instant updatedAt
) {}

