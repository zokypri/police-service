package se.implementer.policeservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import se.implementer.policeservice.model.PoliceEvent;
import se.implementer.policeservice.model.PoliceApiParams;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class PoliceClient {

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "https://polisen.se/api/events";

    public PoliceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<PoliceEvent> getPoliceNews(String city, String date, String type) {
        log.info("fetch data from police");
        var response =  restTemplate.exchange(
                addQueryParams(city, date, type),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<PoliceEvent>>() {}
        );
        var events = response.getBody();
        log.info("fetched data from police");
        return events;
    }

    private String addQueryParams(String city, String date, String type) {
        return UriComponentsBuilder
                .fromUriString(BASE_URL)
                .queryParamIfPresent(PoliceApiParams.CITY.getParamType(), Optional.ofNullable(city))
                .queryParamIfPresent(PoliceApiParams.TYPE.getParamType(), Optional.ofNullable(type))
                .queryParamIfPresent(PoliceApiParams.DATE.getParamType(), Optional.ofNullable(date))
                .build()
                .toString();
    }
}
