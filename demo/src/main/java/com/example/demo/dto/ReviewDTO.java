package com.example.demo.dto;

import java.util.List;

public record ReviewDTO (
        Long Id,
        SimpleBook book,
        String title,
        String body,
        Integer rating
) {}
