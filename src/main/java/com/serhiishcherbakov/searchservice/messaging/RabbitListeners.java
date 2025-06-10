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
    public void onNotebookSaved(BaseMessage<List<Notebook>> message) {
        log.info("Received notebook message: type={}, payload_size={}", message.getType(), message.getPayload().size());

        switch (message.getType()) {
            case NOTEBOOK_CREATED, NOTEBOOK_UPDATED -> notebookService.saveNotebooks(message.getPayload());
            case NOTEBOOK_DELETED -> notebookService.deleteNotebooks(message.getPayload());
        }

        log.info("Successfully processed {} notebooks for {}", message.getPayload().size(), message.getType());
    }

    @RabbitListener(queues = "${rabbitmq.queues.tags-changed}")
    public void onNotebookDeleted(BaseMessage<List<Tag>> message) {
        log.info("Received tag message: type={}, payload_size={}", message.getType(), message.getPayload().size());

        message.getPayload().forEach(tag -> {
            try {
                switch (message.getType()) {
                    case TAG_CREATED, TAG_UPDATED -> notebookService.saveTag(tag);
                    case TAG_DELETED -> notebookService.deleteTag(tag);
                }
            } catch (Exception e) {
                log.error("Failed to process tag message: {}", e.getMessage());
            }
        });

        log.info("Successfully processed {} tags for {}", message.getPayload().size(), message.getType());
    }
}
