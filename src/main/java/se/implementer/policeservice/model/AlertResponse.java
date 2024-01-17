package se.implementer.policeservice.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AlertResponse {

    // TODO add validation for minimum number and not negative
    int alertId;

    // TODO add validation for minimum and maximum size
    String msg;

}
