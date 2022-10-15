package se.implementer.policeservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import se.implementer.policeservice.model.PoliceEvent;

import java.util.List;

@Component
@Slf4j
public class PoliceClient {

    private final RestTemplate restTemplate;

    private static final String URL = "?locationname=Solna&DateTime=2022-10";
    private static final String BASE_URL = "https://polisen.se/api/events";

    public PoliceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<PoliceEvent> getPoliceNews() {
        log.info("fetch data from police");
        var response =  restTemplate.exchange(
                BASE_URL + URL,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<PoliceEvent>>() {});

        var events = response.getBody();
        log.info("fetched data from police");
        return events;
    }
}
