package org.practice_user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.practice_user.dto.DocumentDto;
import org.practice_user.entity.DocumentEntity;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocumentMapper {

    @Mapping(source = "documentNumber", target = "number")
    @Mapping(target = "documentType", constant = "PASSPORT")
    DocumentDto toDto(DocumentEntity documentEntity);

    List<DocumentDto> toDtoList(List<DocumentEntity> documentEntities);

    @Mapping(source = "number", target = "documentNumber")
    @Mapping(target = "documentType", constant = "PASSPORT")
    DocumentEntity toEntity(DocumentDto documentDto);

    List<DocumentEntity> toEntityList(List<DocumentDto> documentDtos);
}