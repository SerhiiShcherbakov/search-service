package com.serhiishcherbakov.searchservice.messaging.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class BaseMessage<T> {
    private EventType type;
    private Instant timestamp;
    private T payload;
}
