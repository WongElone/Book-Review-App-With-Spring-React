package com.example.demo.dto;


import java.time.Instant;

public record ReviewDTO (
        Long Id,
        SimpleBook book,
        String title,
        String body,
        Integer rating,
        Instant createdDate
) {}
