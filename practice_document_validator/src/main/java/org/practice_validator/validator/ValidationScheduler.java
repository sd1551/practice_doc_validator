package org.practice_validator.validator;

import org.practice_validator.DocumentRepository;
import org.practice_validator.entity.DocumentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;

@Component
public class ValidationScheduler {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private List<Validator> validators;

    @Value("${validator.scheduler.timer}")
    private long schedulerTimer;

    @Scheduled(fixedRateString = "${validator.scheduler.timer}")
    public void validateDocuments() {
        List<DocumentEntity> documents = documentRepository.findUnvalidatedDocuments();
        LOGGER.info("Найдено документов для валидации: " + documents.size());

        Map<DocumentEntity.DocumentType, Validator> validatorMap = validators.stream()
                .collect(Collectors.toMap(Validator::getTypeValidation, v -> v));

        for (DocumentEntity document : documents) {
            Validator validator = validatorMap.get(document.getDocumentType());
            if (validator != null) {
                boolean isValid = validator.applicableValidation(document);
                document.setValidationResult(isValid);
                documentRepository.save(document);
            }
        }
    }
}