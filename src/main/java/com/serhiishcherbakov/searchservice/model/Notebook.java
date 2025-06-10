package com.serhiishcherbakov.searchservice.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@Document(indexName = "notebooks")
public class Notebook {
    @Id
    @Field(type = FieldType.Keyword)
    private final Long id;
    private final String title;
    @Field(type = FieldType.Text)
    private final String body;
    @Field(type = FieldType.Nested)
    private final List<Tag> tags;
    @Field(type = FieldType.Keyword)
    private final String userId;
    @Field(type = FieldType.Date)
    private final Instant createdAt;
    @Field(type = FieldType.Date)
    private final Instant updatedAt;
    @Field(type = FieldType.Date)
    private final Instant deletedAt;
}
