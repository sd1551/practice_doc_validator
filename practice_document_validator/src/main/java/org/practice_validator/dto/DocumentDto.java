package org.practice_validator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto {
    private String documentId;
    private String userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String number;
    private DocumentType documentType;
    private boolean validationResult;

    public enum DocumentType {
        PASSPORT
    }
}