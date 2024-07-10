package org.practice_validator.kafka;

import org.practice_validator.DocumentRepository;
import org.practice_validator.mapper.DocumentMapper;
import org.practice_validator.validator.Validator;
import org.practice_validator.dto.DocumentDto;
import org.practice_validator.entity.DocumentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentParserResultConsumer {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private List<Validator> validators;

    @KafkaListener(topics = "result-parser-document", groupId = "group_id")
    public void consume(DocumentDto documentDto) {
        try {
            DocumentEntity documentEntity = DocumentMapper.INSTANCE.toEntity(documentDto);

            DocumentEntity existingDocument = documentRepository.findById(documentEntity.getDocumentId())
                    .orElse(documentEntity);

            Validator validator = validators.stream()
                    .filter(v -> v.getTypeValidation() == existingDocument.getDocumentType())
                    .findFirst()
                    .orElse(null);

            if (validator != null) {
                boolean isValid = validator.applicableValidation(existingDocument);
                existingDocument.setValidationResult(isValid);
            } else {
                existingDocument.setValidationResult(false);
            }

            documentRepository.save(existingDocument);
        } catch (Exception e) {
            System.err.println("Ошибка обработки документа: " + e.getMessage());
            e.printStackTrace();
        }
    }
}