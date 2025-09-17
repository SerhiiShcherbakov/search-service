package com.serhiishcherbakov.searchservice.messaging.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class BaseMessage<T> {
    private String id;
    private EventType type;
    private Instant createdAt;
    private T data;
}
