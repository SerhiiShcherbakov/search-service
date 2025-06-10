package com.serhiishcherbakov.searchservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange topicExchange(RabbitProperties rabbitProperties) {
        return new TopicExchange(rabbitProperties.exchanges().notebooks(), true, false);
    }

    @Bean
    public Queue notebookQueue(RabbitProperties rabbitProperties) {
        return new Queue(rabbitProperties.queues().notebooksChanged(), true);
    }

    @Bean
    public Queue tagQueue(RabbitProperties rabbitProperties) {
        return new Queue(rabbitProperties.queues().tagsChanged(), true);
    }

    @Bean
    public Binding bindingNotebookCreated(Queue notebookQueue,
                                          TopicExchange topicExchange,
                                          RabbitProperties rabbitProperties) {
        return BindingBuilder.bind(notebookQueue)
                .to(topicExchange)
                .with(rabbitProperties.routingKeys().notebooksCreated());
    }

    @Bean
    public Binding bindingNotebookUpdated(Queue notebookQueue,
                                          TopicExchange topicExchange,
                                          RabbitProperties rabbitProperties) {
        return BindingBuilder.bind(notebookQueue)
                .to(topicExchange)
                .with(rabbitProperties.routingKeys().notebooksUpdated());
    }

    @Bean
    public Binding bindingNotebookDeleted(Queue notebookQueue,
                                          TopicExchange topicExchange,
                                          RabbitProperties rabbitProperties) {
        return BindingBuilder.bind(notebookQueue)
                .to(topicExchange)
                .with(rabbitProperties.routingKeys().notebooksDeleted());
    }

    @Bean
    public Binding bindingTagUpdated(Queue tagQueue,
                                     TopicExchange topicExchange,
                                     RabbitProperties rabbitProperties) {
        return BindingBuilder.bind(tagQueue)
                .to(topicExchange)
                .with(rabbitProperties.routingKeys().tagsUpdated());
    }

    @Bean
    public Binding bindingTagDeleted(Queue tagQueue,
                                     TopicExchange topicExchange,
                                     RabbitProperties rabbitProperties) {
        return BindingBuilder.bind(tagQueue)
                .to(topicExchange)
                .with(rabbitProperties.routingKeys().tagsDeleted());
    }
}
