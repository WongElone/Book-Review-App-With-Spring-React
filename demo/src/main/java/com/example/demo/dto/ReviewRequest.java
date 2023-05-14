package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewRequest (
        @NotBlank
        String title,
        @NotBlank
        String body,
        @NotNull
        Integer rating
) {}
