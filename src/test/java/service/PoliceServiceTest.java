package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.implementer.policeservice.client.PoliceClient;
import se.implementer.policeservice.service.PoliceService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static utils.TestData.*;

@ExtendWith(SpringExtension.class)
public class PoliceServiceTest {

    @Mock
    private PoliceClient policeClient;

    @InjectMocks
    private PoliceService policeService;

    @BeforeEach
    void setup() {
        when(policeClient.getPoliceNews(any(), any(), any()))
                .thenReturn(createPoliceEvents());
    }

    @Test
    void shouldReturnPoliceEvents() {
        assertEquals(createPoliceEvents(), policeService.getPoliceNews(null, null, null));
    }

    @Test
    void shouldReturnPoliceEventWithTypeFire() {
        var eventTypeFire = createPoliceEventTypeFire();
        var actualEvent = policeService.getPoliceNews(null, null, "Brand");

        assertEquals(eventTypeFire, actualEvent);
    }

    @Test
    void shouldReturnPoliceEventWithTypeFireInSolna() {
        var eventTypeFire = createPoliceEventTypeFire();
        var actualEvent = policeService.getPoliceNews("Solna", null, "Brand");

        assertEquals(eventTypeFire, actualEvent );
    }

    @Test
    void shouldNotReturnPoliceEventsWhenTypeMurder() {
        assertEquals(List.of(), policeService.getPoliceNews(null, null, "Mord") );
    }

    @Test
    void shouldNotReturnPoliceEventWhenCityStockholm() {
        when(policeClient.getPoliceNews(eq("Stockholm"), any(), any()))
                .thenReturn(List.of());

        var actualEvent = policeService.getPoliceNews("Stockholm", null, null);

        assertEquals(List.of(), actualEvent);
    }
}
