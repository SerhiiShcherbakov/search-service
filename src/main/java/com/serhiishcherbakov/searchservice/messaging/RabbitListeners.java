package com.serhiishcherbakov.searchservice.messaging;

import com.serhiishcherbakov.searchservice.messaging.model.BaseMessage;
import com.serhiishcherbakov.searchservice.model.Notebook;
import com.serhiishcherbakov.searchservice.model.Tag;
import com.serhiishcherbakov.searchservice.service.NotebookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitListeners {
    private final NotebookService notebookService;

    @RabbitListener(queues = "${rabbitmq.queues.notebooks-changed}")
    public void onNotebookChange(BaseMessage<Notebook> message) {
        log.info("Received notebook message: notebook={}, type={}", message.getData().getId(), message.getType());

        switch (message.getType()) {
            case NOTEBOOK_CREATED, NOTEBOOK_UPDATED -> notebookService.saveNotebook(message.getData());
            case NOTEBOOK_DELETED -> notebookService.deleteNotebook(message.getData());
            default -> throw new IllegalArgumentException("Incompatible event type for event=" + message.getId());
        }

        log.info("Successfully processed notebook event {} for {}", message.getId(), message.getType());
    }

    @RabbitListener(queues = "${rabbitmq.queues.tags-changed}")
    public void onTagChange(BaseMessage<Tag> message) {
        log.info("Received tag message: tag={}, type={}", message.getData().getId(), message.getType());

        switch (message.getType()) {
            case TAG_UPDATED -> notebookService.saveTag(message.getData());
            case TAG_DELETED -> notebookService.deleteTag(message.getData());
            default -> throw new IllegalArgumentException("Incompatible event type for event=" + message.getId());
        }

        log.info("Successfully processed tag event {} for {}", message.getId(), message.getType());
    }
}
