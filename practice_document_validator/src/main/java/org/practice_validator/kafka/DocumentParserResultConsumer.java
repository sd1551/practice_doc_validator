package org.practice_validator.kafka;

import org.practice_validator.DocumentRepository;
import org.practice_validator.validator.Validator;
import org.practice_validator.dto.DocumentDto;
import org.practice_validator.entity.DocumentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DocumentParserResultConsumer {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private List<Validator> validators;

    @KafkaListener(topics = "result-parser-document", groupId = "group_id")
    public void consume(DocumentDto documentDto) {
        try {
            DocumentEntity documentEntity = documentRepository.findById(documentDto.getDocumentId())
                    .orElse(new DocumentEntity());

            documentEntity.setDocumentId(documentDto.getDocumentId());
            documentEntity.setUserId(UUID.fromString(documentDto.getUserId()));
            documentEntity.setFirstName(documentDto.getFirstName());
            documentEntity.setLastName(documentDto.getLastName());
            documentEntity.setMiddleName(documentDto.getMiddleName());
            documentEntity.setDocumentNumber(documentDto.getNumber());
            documentEntity.setDocumentType(DocumentEntity.DocumentType.PASSPORT);

            Validator validator = validators.stream()
                    .filter(v -> v.getTypeValidation() == documentEntity.getDocumentType())
                    .findFirst()
                    .orElse(null);

            if (validator != null) {
                boolean isValid = validator.applicableValidation(documentEntity);
                documentEntity.setValidationResult(isValid);
            } else {
                documentEntity.setValidationResult(false);
            }

            documentRepository.save(documentEntity);
        } catch (Exception e) {
            System.err.println("Ошибка обработки документа: " + e.getMessage());
            e.printStackTrace();
        }
    }
}