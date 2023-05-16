package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record AuthorRequest (
        @NotBlank @Size(min = 3, max = 255)
        String fullName,
        @JsonProperty("books")
        List<Long> bookIds
) {}
