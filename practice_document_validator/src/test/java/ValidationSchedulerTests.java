import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.practice_validator.DocumentRepository;
import org.practice_validator.entity.DocumentEntity;
import org.practice_validator.validator.ValidationScheduler;
import org.practice_validator.validator.Validator;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class ValidationSchedulerTests {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private List<Validator> validators;

    @InjectMocks
    private ValidationScheduler validationScheduler;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidateDocuments() {
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setDocumentId(UUID.randomUUID().toString());
        documentEntity.setDocumentType(DocumentEntity.DocumentType.PASSPORT);
        documentEntity.setDocumentNumber("1234 56 789012");

        Validator validator = mock(Validator.class);
        when(validator.getTypeValidation()).thenReturn(DocumentEntity.DocumentType.PASSPORT);
        when(validator.applicableValidation(any(DocumentEntity.class))).thenReturn(true);
        when(documentRepository.findUnvalidatedDocuments()).thenReturn(List.of(documentEntity));
        when(validators.stream()).thenReturn(List.of(validator).stream());

        validationScheduler.validateDocuments();

        verify(documentRepository, times(1)).save(documentEntity);
    }
}