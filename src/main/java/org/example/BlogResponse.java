package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// public 1개만 존재할 수 있음
public record BlogResponse(
   List<BlogItem> items
) {
    public record BlogItem(
            String title,
            String link,
            String description,
            String bloggername,
            String bloggerlink,
            String postdate
    ) {}
}
