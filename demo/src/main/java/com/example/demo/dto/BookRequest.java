package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record BookRequest (
        @NotBlank @Size(min = 3, max = 255)
        String title,
        @NotBlank @Size(max = 1000)
        String description,
        @JsonProperty("authors")
        List<Long> authorIds,
        String coverImageRelativeUri
) {}
