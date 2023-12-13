package se.implementer.policeservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class EventLocation {

    @JsonProperty("name")
    String city;

    String gps;

}
