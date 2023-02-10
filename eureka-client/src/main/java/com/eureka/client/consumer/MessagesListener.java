package com.eureka.client.consumer;

import com.eureka.client.exception.RequestBuildException;
import com.eureka.client.model.Translation;
import com.eureka.client.service.TranslateCrudService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessagesListener {
    private final TranslateCrudService translateCrudService;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "${topic.name}", groupId = "${consumer.group.id}")
    public void saveMessage(String message) {
        Translation translation = null;
        try {
            translation = mapper.readValue(message, Translation.class);
            translateCrudService.save(translation);
        } catch (JsonProcessingException e) {
            throw new RequestBuildException("Broker message exception.", e);
        }
    }
}
