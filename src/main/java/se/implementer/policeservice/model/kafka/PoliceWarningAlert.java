package se.implementer.policeservice.model.kafka;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PoliceWarningAlert {

    @NotNull
    @Min(value = 1)
    int id;

    @NotNull
    @NotBlank(message = "Alert message is mandatory")
    @Size(max = 255, message = "Can't be longer than 255 chars")
    String msg;

    WarningLevel warningLevel;
}
