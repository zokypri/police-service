package se.implementer.policeservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.implementer.policeservice.client.PoliceClient;
import se.implementer.policeservice.model.PoliceEvent;

import java.util.List;

@Service
@Slf4j
public class PoliceService {

    private final PoliceClient policeClient;

    public PoliceService(PoliceClient policeClient) {
        this.policeClient = policeClient;
    }

    public List<PoliceEvent> getPoliceNews(String city, String date, String type) {
        log.info("Preparing police news");
        return filterByEventType(policeClient.getPoliceNews(city, date, type), type);
    }

    public List<PoliceEvent> filterByEventType(List<PoliceEvent> events, String type) {
        return type == null ? events : events
                .stream()
                .filter(policeEvent -> policeEvent.getType().equals(type))
                .toList();
    }
}
