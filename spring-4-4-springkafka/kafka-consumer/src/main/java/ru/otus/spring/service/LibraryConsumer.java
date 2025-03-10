package ru.otus.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.LibraryMessage;

/**
 * LibraryConsumer
 **/
@Slf4j
@Service
public class LibraryConsumer {

    @KafkaListener(
            topics = "${application.kafka.topic}",
            containerFactory = "listenerContainerFactory")
    public void listen(LibraryMessage message) {
        log.info("Received message with body: {}", message);
    }

}
