package org.practice_user;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DocumentMapper {

    public static DocumentDto toDto(DocumentEntity documentEntity) {
        DocumentDto documentDto = new DocumentDto();
        documentDto.setDocumentId(documentEntity.getDocumentId());
        documentDto.setUserId(documentEntity.getUserId().toString());
        documentDto.setLastName(documentEntity.getLastName());
        documentDto.setFirstName(documentEntity.getFirstName());
        documentDto.setMiddleName(documentEntity.getMiddleName());
        documentDto.setNumber(documentEntity.getDocumentNumber());
        documentDto.setDocumentType(DocumentDto.DocumentType.PASSPORT);
        documentDto.setValidationResult(documentEntity.getValidationResult());
        return documentDto;
    }

    public static List<DocumentDto> toDtoList(List<DocumentEntity> documentEntities) {
        return documentEntities.stream().map(DocumentMapper::toDto).collect(Collectors.toList());
    }

    public static DocumentEntity toEntity(DocumentDto documentDto) {
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setDocumentId(documentDto.getDocumentId());
        documentEntity.setUserId(UUID.fromString(documentDto.getUserId()));
        documentEntity.setLastName(documentDto.getLastName());
        documentEntity.setFirstName(documentDto.getFirstName());
        documentEntity.setMiddleName(documentDto.getMiddleName());
        documentEntity.setDocumentNumber(documentDto.getNumber());
        documentEntity.setDocumentType(DocumentEntity.DocumentType.PASSPORT);
        return documentEntity;
    }

    public static List<DocumentEntity> toEntityList(List<DocumentDto> documentDtos) {
        return documentDtos.stream().map(DocumentMapper::toEntity).collect(Collectors.toList());
    }
}