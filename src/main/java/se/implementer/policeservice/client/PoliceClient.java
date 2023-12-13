package se.implementer.policeservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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


@Slf4j
@Component
public class PoliceClient {

    private final RestTemplate restTemplate;

    private final String baseUrl;

    public PoliceClient(RestTemplate restTemplate, @Value("${police.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public List<PoliceEvent> getPoliceNews(String city, String date, String type) {
        log.info("Fetching data from police api");
        var response =  restTemplate.exchange(
                addQueryParams(city, date, type),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<PoliceEvent>>() {}
        );
        var events = response.getBody();
        log.info("Fetched data from police api");
        return events;
    }

    private String addQueryParams(String city, String date, String type) {
        return UriComponentsBuilder
                .fromUriString(baseUrl)
                .queryParamIfPresent(PoliceApiParams.CITY.getParamType(), Optional.ofNullable(city))
                .queryParamIfPresent(PoliceApiParams.TYPE.getParamType(), Optional.ofNullable(type))
                .queryParamIfPresent(PoliceApiParams.DATE.getParamType(), Optional.ofNullable(date))
                .build()
                .toString();
    }
}
