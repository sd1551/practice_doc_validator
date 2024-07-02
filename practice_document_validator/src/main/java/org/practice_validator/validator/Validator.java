package org.practice_validator.validator;

import org.practice_validator.entity.DocumentEntity;

public interface Validator {
    boolean applicableValidation(DocumentEntity documentEntity);
    DocumentEntity.DocumentType getTypeValidation();
}