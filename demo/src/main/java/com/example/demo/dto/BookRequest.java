package com.example.demo.dto;

import com.example.demo.model.Author;
import com.example.demo.model.Review;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BookRequest (
        @NotBlank
        String title,
        @NotBlank
        String description,
        @JsonProperty("authors")
        List<Long> authorIds,
        List<Review> reviews
) {}
