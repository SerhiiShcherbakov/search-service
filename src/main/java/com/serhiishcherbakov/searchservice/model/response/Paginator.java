package com.serhiishcherbakov.searchservice.model.response;

public record Paginator(int page, int size, long total) {
}
