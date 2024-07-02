package org.practice_parser.producer;

import org.practice_parser.dto.DocumentDto;
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

    public void send(DocumentDto documentDto) {
        kafkaTemplate.send("result-parser-document", documentDto);
    }
}