import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.practice_parser.dto.DocumentDto;
import org.practice_parser.handler.PassportParserHandlerImpl;

import static org.junit.jupiter.api.Assertions.*;

public class PassportParserHandlerImplTest {

    private PassportParserHandlerImpl passportParserHandler;

    @BeforeEach
    public void setUp() {
        passportParserHandler = new PassportParserHandlerImpl();
    }

    @Test
    public void testParseDocument() {
        String filePath = "src/main/resources/static/111.jpg";
        DocumentDto documentDto = passportParserHandler.parseDocument(filePath);
        assertNotNull(documentDto);
        assertEquals(DocumentDto.DocumentType.PASSPORT, documentDto.getDocumentType());
        assertNotNull(documentDto.getFirstName());
        assertNotNull(documentDto.getLastName());
        assertNotNull(documentDto.getMiddleName());
        assertNotNull(documentDto.getNumber());
    }

    @Test
    public void testParseDocumentInvalidFile() {
        String filePath = "src/main/resources/static/4edfbc1s-1920.jpg";
        DocumentDto documentDto = passportParserHandler.parseDocument(filePath);
        assertNull(documentDto);
    }
}