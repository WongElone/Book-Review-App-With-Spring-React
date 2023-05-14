package com.example.demo.dto;

import java.util.List;

public record AuthorDTO (
        Long Id,
        String fullName,
        List<SimpleBook> books
) {}
