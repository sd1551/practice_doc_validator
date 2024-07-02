package org.practice_parser.consumer;

import org.practice_parser.dto.DocumentDto;
import org.practice_parser.handler.DocumentParserHandler;
import org.practice_parser.producer.DocumentParserResultProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.File;
import java.util.UUID;

@Service
public class DocumentConsumer {

    @Autowired
    private DocumentParserHandler documentParserHandler;

    @Autowired
    private DocumentParserResultProducer documentParserResultProducer;

    @KafkaListener(topics = "parser-document", groupId = "group_id")
    public void consume(ConsumerRecord<String, String> record) {
        String[] message = record.value().split(";");
        String filePath = message[0];
        UUID documentId = UUID.fromString(message[1]);
        UUID userId = UUID.fromString(message[2]);

        File file = new File(filePath);

        if (!file.exists() || !file.isFile()) {
            System.err.println("Файл не найден: " + file.getPath());
            return;
        }

        DocumentDto documentDto = documentParserHandler.parseDocument(file.getPath());
        if (documentDto != null) {
            documentDto.setDocumentId(String.valueOf(documentId));
            documentDto.setUserId(String.valueOf(userId));
            documentParserResultProducer.send(documentDto);
        } else {
            System.err.println("Ошибка обработки файла: " + file.getPath());
        }
    }
}