package se.implementer.policeservice.model.kafka;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PoliceWarningAlert {
    //TODO validate input
    int id;

    String msg;

    WarningLevel warningLevel;
}
