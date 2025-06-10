package com.serhiishcherbakov.searchservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotebookSearchOrder {
    BEST_MATCH("_score", true),
    TITLE_ASC("title", true),
    TITLE_DESC("title", false),
    CREATED_AT_ASC("createdAt", true),
    CREATED_AT_DESC("createdAt", false),
    UPDATED_AT_ASC("updatedAt", true),
    UPDATED_AT_DESC("updatedAt", false);

    private final String field;
    private final boolean ascending;
}
