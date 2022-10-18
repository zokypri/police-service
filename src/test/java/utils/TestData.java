package utils;

import se.implementer.policeservice.model.EventLocation;
import se.implementer.policeservice.model.PoliceEvent;

import java.util.List;

public class TestData {

    public static List<PoliceEvent> createPoliceEvents() {
        return List.of(
                PoliceEvent
                        .builder()
                        .id(1223)
                        .description("desc")
                        .type("Brand")
                        .location(EventLocation.builder().name("Solna").build())
                        .build(),
                PoliceEvent
                        .builder()
                        .id(1224)
                        .description("desc")
                        .type("Rattfylleri")
                        .location(EventLocation.builder().name("Skellefteå").build())
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
                        .location(EventLocation.builder().name("Solna").build())
                        .build()
        );
    }
}
