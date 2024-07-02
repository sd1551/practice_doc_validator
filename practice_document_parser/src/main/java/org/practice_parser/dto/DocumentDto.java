package org.practice_parser.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class DocumentDto {
    private String documentId;
    private String userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String number;
    private DocumentType documentType;

    public enum DocumentType {
        PASSPORT
    }

    public DocumentDto(String firstName, String lastName, String middleName, String number, DocumentType documentType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.number = number;
        this.documentType = documentType;
    }

    private boolean validationResult;
}