package com.example.demo.dto;

import java.util.List;

public record AuthorResponse (
        Long Id,
        String fullName,
        List<SimpleBook> books
) {}
