import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.practice_user.dto.DocumentDto;
import org.practice_user.kafka.DocumentParserResultProducer;
import org.springframework.kafka.core.KafkaTemplate;

public class DocumentParserResultProducerTests {

    @Mock
    private KafkaTemplate<String, DocumentDto> kafkaTemplate;

    @InjectMocks
    private DocumentParserResultProducer producer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendResult() {
        String topic = "test-topic";
        DocumentDto documentDto = new DocumentDto();

        producer.sendResult(topic, documentDto);

        verify(kafkaTemplate, times(1)).send(topic, documentDto);
    }
}