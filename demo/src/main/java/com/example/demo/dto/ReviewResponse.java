package com.example.demo.dto;


import java.time.Instant;

public record ReviewResponse(
        Long Id,
        SimpleBook book,
        String title,
        String body,
        Integer rating,
        Instant createdAt
) {}
