package org.practice_validator.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.practice_validator.dto.DocumentDto;
import org.practice_validator.entity.DocumentEntity;

@Mapper
public interface DocumentMapper {
    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

    @Mapping(source = "number", target = "documentNumber")
    DocumentEntity toEntity(DocumentDto documentDto);

    @Mapping(source = "documentNumber", target = "number")
    DocumentDto toDto(DocumentEntity documentEntity);
}