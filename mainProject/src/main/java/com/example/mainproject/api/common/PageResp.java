package com.example.mainproject.api.common;

import java.util.List;

public record PageResp<T>(
        List<T> content,
        long totalElements,
        int totalPages,
        int number,
        int size
) {}
