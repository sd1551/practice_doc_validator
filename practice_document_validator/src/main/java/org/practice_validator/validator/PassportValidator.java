package org.practice_validator.validator;

import org.practice_validator.entity.DocumentEntity;
import org.springframework.stereotype.Service;

@Service
public class PassportValidator implements Validator {

    @Override
    public boolean applicableValidation(DocumentEntity documentEntity) {
        String documentNumber = documentEntity.getDocumentNumber();

        if (documentNumber == null || !documentNumber.matches("\\d{2} \\d{2} \\d{6}")) {
            return false;
        }

        String[] parts = documentNumber.split(" ");
        return parts.length == 3 && parts[0].length() == 2 && parts[1].length() == 2 && parts[2].length() == 6;
    }

    @Override
    public DocumentEntity.DocumentType getTypeValidation() {
        return DocumentEntity.DocumentType.PASSPORT;
    }
}