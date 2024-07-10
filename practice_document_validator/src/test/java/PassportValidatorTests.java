import org.junit.jupiter.api.Test;
import org.practice_validator.entity.DocumentEntity;
import org.practice_validator.validator.PassportValidator;

import static org.junit.jupiter.api.Assertions.*;

public class PassportValidatorTests {

    private final PassportValidator passportValidator = new PassportValidator();

    @Test
    public void testApplicableValidation_ValidDocumentNumber() {
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setDocumentNumber("12 34 567890");

        assertTrue(passportValidator.applicableValidation(documentEntity));
    }

    @Test
    public void testApplicableValidation_InvalidDocumentNumber() {
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setDocumentNumber("1234567890");

        assertFalse(passportValidator.applicableValidation(documentEntity));
    }

    @Test
    public void testGetTypeValidation() {
        assertEquals(DocumentEntity.DocumentType.PASSPORT, passportValidator.getTypeValidation());
    }
}