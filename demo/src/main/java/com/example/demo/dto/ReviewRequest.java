package com.example.demo.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;

public record ReviewRequest (
        @NotBlank @Size(min = 3, max = 255)
        String title,
        @NotBlank @Lob
        String body,
        @NotNull @Min(1) @Max(5)
        Integer rating
) {}
