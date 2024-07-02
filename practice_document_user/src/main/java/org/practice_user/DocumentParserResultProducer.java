package org.practice_user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class DocumentParserResultProducer {

    private final KafkaTemplate<String, DocumentDto> kafkaTemplate;

    @Autowired
    public DocumentParserResultProducer(KafkaTemplate<String, DocumentDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendResult(String topic, DocumentDto payload) {
        kafkaTemplate.send(topic, payload);
    }
}