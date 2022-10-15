package se.implementer.policeservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class PoliceEvent {

    int id;

    @JsonProperty("datetime")
    String eventTime;

    @JsonProperty("name")
    String description;

    String summary;

    String url;

    String type;

    @JsonProperty("location")
    EventLocation location;

}
