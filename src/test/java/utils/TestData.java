package utils;

import se.implementer.policeservice.model.EventLocation;
import se.implementer.policeservice.model.PoliceEvent;

import java.util.List;

public class TestData {

    public static List<PoliceEvent> createPoliceEventsMocked() {
        return List.of(
                PoliceEvent
                        .builder()
                        .id(1223)
                        .description("desc")
                        .type("Brand")
                        .url("/url")
                        .location(EventLocation.builder().city("Solna").build())
                        .build(),
                PoliceEvent
                        .builder()
                        .id(1224)
                        .url("/url")
                        .description("desc")
                        .type("Rattfylleri")
                        .location(EventLocation.builder().city("Skellefteå").build())
                        .build()
        );
    }

    public static List<PoliceEvent> createPoliceEvents() {
        return List.of(
                PoliceEvent
                        .builder()
                        .id(1223)
                        .description("desc")
                        .type("Brand")
                        .url("https://polisen.se/url")
                        .location(EventLocation.builder().city("Solna").build())
                        .build(),
                PoliceEvent
                        .builder()
                        .id(1224)
                        .description("desc")
                        .url("https://polisen.se/url")
                        .type("Rattfylleri")
                        .location(EventLocation.builder().city("Skellefteå").build())
                        .build()
        );
    }

    public static List<PoliceEvent> createPoliceEventTypeFire() {
        return List.of(
                PoliceEvent
                        .builder()
                        .id(1223)
                        .description("desc")
                        .type("Brand")
                        .url("https://polisen.se/url")
                        .location(EventLocation.builder().city("Solna").build())
                        .build()
        );
    }
}
