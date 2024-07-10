import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.practice_parser.dto.DocumentDto;
import org.practice_parser.producer.DocumentParserResultProducer;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

public class DocumentParserResultProducerTest {

    @Mock
    private KafkaTemplate<String, DocumentDto> kafkaTemplate;

    @InjectMocks
    private DocumentParserResultProducer documentParserResultProducer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSend() {
        DocumentDto documentDto = new DocumentDto();
        documentParserResultProducer.send(documentDto);

        verify(kafkaTemplate, times(1)).send("result-parser-document", documentDto);
    }
}
