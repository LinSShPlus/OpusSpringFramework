package ru.otus.spring.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;

/**
 * KafkaMessageSender
 **/
@Slf4j
@Builder
@RequiredArgsConstructor
public class KafkaMessageSender implements MessageSender {

    private final String topic;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void send(Object object) {
        try {
            final String jsonMessage = objectMapper.writeValueAsString(object);
            log.info("Send message to {}, body: {}", topic, jsonMessage);

            kafkaTemplate.send(MessageBuilder
                    .withPayload(object)
                    .setHeader(KafkaHeaders.TOPIC, topic)
                    .build());
        } catch (Exception ex) {
            log.error("Could not send message: " + ex.getMessage(), ex);
        }
    }

}
