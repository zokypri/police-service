package se.implementer.policeservice.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AlertResponse {

    int alertId;

    String msg;

}
