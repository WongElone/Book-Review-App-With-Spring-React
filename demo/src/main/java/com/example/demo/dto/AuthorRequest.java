package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record AuthorRequest (
        @NotBlank
        String fullName,
        @JsonProperty("books")
        List<Long> bookIds
) {}
