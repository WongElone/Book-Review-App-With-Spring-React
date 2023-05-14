package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record BookRequest (
        @NotBlank
        String title,
        @NotBlank
        String description,
        @JsonProperty("authors")
        List<Long> authorIds
) {}
