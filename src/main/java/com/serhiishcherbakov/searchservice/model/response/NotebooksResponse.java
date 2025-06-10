package com.serhiishcherbakov.searchservice.model.response;

import com.serhiishcherbakov.searchservice.model.Notebook;

import java.util.List;

public record NotebooksResponse(List<Notebook> notebooks, Paginator paginator) {
}
