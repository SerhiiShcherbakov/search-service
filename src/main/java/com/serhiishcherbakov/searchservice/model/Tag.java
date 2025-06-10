package com.serhiishcherbakov.searchservice.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Getter
@Builder(toBuilder = true)
public class Tag {
    @Field(type = FieldType.Keyword)
    private Long id;
    private String title;
    @Field(type = FieldType.Keyword)
    private String color;
    @Field(type = FieldType.Keyword)
    private String userId;
    @Field(type = FieldType.Date)
    private Instant createdAt;
    @Field(type = FieldType.Date)
    private Instant updatedAt;
}
