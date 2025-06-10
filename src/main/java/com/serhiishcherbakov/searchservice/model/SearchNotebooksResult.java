package com.serhiishcherbakov.searchservice.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchNotebooksResult {
    private List<Notebook> notebooks;
    private int page;
    private int size;
    private long total;
}
