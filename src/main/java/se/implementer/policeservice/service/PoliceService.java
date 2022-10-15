package se.implementer.policeservice.service;

import org.springframework.stereotype.Service;
import se.implementer.policeservice.client.PoliceClient;
import se.implementer.policeservice.model.PoliceEvent;

import java.util.List;

@Service
public class PoliceService {

    private final PoliceClient policeClient;

    public PoliceService(PoliceClient policeClient) {
        this.policeClient = policeClient;
    }

    public List<PoliceEvent> getPoliceNews(String city, String date, String type) {

        return policeClient.getPoliceNews(city, date, type) ;
    }
}
