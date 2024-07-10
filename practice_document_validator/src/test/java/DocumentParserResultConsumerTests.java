import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.practice_validator.DocumentRepository;
import org.practice_validator.dto.DocumentDto;
import org.practice_validator.entity.DocumentEntity;
import org.practice_validator.kafka.DocumentParserResultConsumer;
import org.practice_validator.validator.Validator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class DocumentParserResultConsumerTests {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private List<Validator> validators;

    @InjectMocks
    private DocumentParserResultConsumer documentParserResultConsumer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConsumeDocument() {
        DocumentDto documentDto = new DocumentDto("1", UUID.randomUUID().toString(),
                "A", "B", "C", "12 34 789012", DocumentDto.DocumentType.PASSPORT, false);
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setDocumentId(documentDto.getDocumentId());
        documentEntity.setUserId(UUID.fromString(documentDto.getUserId()));
        documentEntity.setDocumentType(DocumentEntity.DocumentType.PASSPORT);
        documentEntity.setDocumentNumber(documentDto.getNumber());

        Validator validator = mock(Validator.class);
        when(validator.getTypeValidation()).thenReturn(DocumentEntity.DocumentType.PASSPORT);
        when(validator.applicableValidation(any(DocumentEntity.class))).thenReturn(true);
        when(documentRepository.findById(documentDto.getDocumentId())).thenReturn(Optional.of(documentEntity));
        when(validators.stream()).thenReturn(List.of(validator).stream());

        documentParserResultConsumer.consume(documentDto);

        verify(documentRepository, times(1)).save(documentEntity);
    }
}