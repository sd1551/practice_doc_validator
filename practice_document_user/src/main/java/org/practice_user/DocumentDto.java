package org.practice_user;

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

    private boolean validationResult;
}