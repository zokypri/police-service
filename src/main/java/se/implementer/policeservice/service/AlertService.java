package se.implementer.policeservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import se.implementer.policeservice.model.AlertResponse;
import se.implementer.policeservice.model.kafka.PoliceWarningAlert;

@Service
public class AlertService {

    @Value(value = "${kafka.producer.topic.policeWarning}")
    private String topicPoliceWarning;

    @Autowired
    private KafkaTemplate<String, PoliceWarningAlert> kafkaTemplate;

    @Autowired
    private Environment environment;

    public boolean isDevProfileActive() {
        return environment.getActiveProfiles().length > 0 &&
                environment.getActiveProfiles()[0].equalsIgnoreCase("dev");
    }

    public AlertResponse sendMessage(PoliceWarningAlert msg) {
        if (isDevProfileActive()) {
            return AlertResponse.builder().alertId(msg.getId()).msg("Dev profile is active so no alert is sent").build();
        }
        kafkaTemplate.send(topicPoliceWarning, msg);
        return AlertResponse.builder().alertId(msg.getId()).msg("Alert success").build();
    }
}
