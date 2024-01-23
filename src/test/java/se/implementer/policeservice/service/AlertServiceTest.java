package se.implementer.policeservice.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;
import se.implementer.policeservice.model.AlertResponse;
import se.implementer.policeservice.model.kafka.PoliceWarningAlert;

@ExtendWith(MockitoExtension.class)
public class AlertServiceTest {

    @Mock
    private KafkaTemplate<String, PoliceWarningAlert> kafkaTemplate;

    @InjectMocks
    private AlertService alertService;

    @Test
    void shouldReturnSuccess() {

        var policeWarning = PoliceWarningAlert.builder().id(1).build();

        when(kafkaTemplate.send(any(), eq(policeWarning))).thenReturn(mock(ListenableFuture.class));

        var expectedResult = AlertResponse.builder().alertId(1).msg("Alert success").build();

        var actualResult = alertService.sendMessage(policeWarning);

        assertEquals(expectedResult, actualResult);

        verify(kafkaTemplate, times(1)).send(any(), any());
    }

    // TODO add Kafka error case tests
}
