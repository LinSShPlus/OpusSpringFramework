package ru.otus.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.otus.spring.message.KafkaMessageSender;
import ru.otus.spring.message.MessageSender;

/**
 * ApplicationConfig
 **/
@Configuration
public class ApplicationConfig {

    private final String topicName;

    public ApplicationConfig(@Value("${application.kafka.topic}") String topicName) {
        this.topicName = topicName;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory(
            KafkaProperties kafkaProperties, ObjectMapper mapper) {
        var properties = kafkaProperties.buildProducerProperties();
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        var kafkaProducerFactory = new DefaultKafkaProducerFactory<String, String>(properties);
        kafkaProducerFactory.setValueSerializer(new JsonSerializer<>(mapper));
        return kafkaProducerFactory;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.
                name(topicName).
                partitions(1).
                replicas(1)
                .build();
    }

    @Bean
    public MessageSender messageSender(NewTopic topic, KafkaTemplate<String, String> kafkaTemplate,
                                       ObjectMapper objectMapper) {
        return KafkaMessageSender
                .builder()
                .topic(topic.name())
                .kafkaTemplate(kafkaTemplate)
                .objectMapper(objectMapper)
                .build();
    }

}
