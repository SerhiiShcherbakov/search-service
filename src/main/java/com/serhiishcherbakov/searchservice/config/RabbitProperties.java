package com.serhiishcherbakov.searchservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rabbitmq")
public record RabbitProperties(Exchanges exchanges,
                               RoutingKeys routingKeys,
                               Queues queues) {

    public record Exchanges(String notebooks) {}

    public record RoutingKeys(String notebooksCreated,
                              String notebooksUpdated,
                              String notebooksDeleted,
                              String tagsUpdated,
                              String tagsDeleted) {}

    public record Queues(String notebooksChanged,
                         String tagsChanged) {}
}
