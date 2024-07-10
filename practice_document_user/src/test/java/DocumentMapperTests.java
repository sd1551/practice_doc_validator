import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.practice_user.dto.DocumentDto;
import org.practice_user.entity.DocumentEntity;
import org.practice_user.DocumentMapper;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentMapperTests {

    private DocumentMapper documentMapper;

    @BeforeEach
    public void setup() {
        documentMapper = Mappers.getMapper(DocumentMapper.class);
    }

    @Test
    public void testToDto() {
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setDocumentNumber("123456");

        DocumentDto documentDto = documentMapper.toDto(documentEntity);

        assertEquals("123456", documentDto.getNumber());
        assertEquals(DocumentDto.DocumentType.PASSPORT, documentDto.getDocumentType());
    }

    @Test
    public void testToEntity() {
        DocumentDto documentDto = new DocumentDto();
        documentDto.setNumber("123456");

        DocumentEntity documentEntity = documentMapper.toEntity(documentDto);

        assertEquals("123456", documentEntity.getDocumentNumber());
        assertEquals(DocumentEntity.DocumentType.PASSPORT, documentEntity.getDocumentType());
    }

    @Test
    public void testToDtoList() {
        List<DocumentEntity> entities = List.of(new DocumentEntity());
        List<DocumentDto> dtos = documentMapper.toDtoList(entities);

        assertEquals(1, dtos.size());
    }

    @Test
    public void testToEntityList() {
        List<DocumentDto> dtos = List.of(new DocumentDto());
        List<DocumentEntity> entities = documentMapper.toEntityList(dtos);

        assertEquals(1, entities.size());
    }
}