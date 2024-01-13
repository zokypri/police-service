package se.implementer.policeservice.service;

import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import se.implementer.policeservice.exception.KafkaPoliceException;
import se.implementer.policeservice.model.AlertResponse;
import se.implementer.policeservice.model.kafka.PoliceWarningAlert;
@Slf4j
@Service
public class AlertService {

    // TODO after removing the config files for Kafka we can add KafkaTemplate to the constructor to be injected
    // no need for a bean creation

    @Value(value = "${kafka.producer.topic.policeWarning}")
    private String topicPoliceWarning;

    private final KafkaTemplate<String, PoliceWarningAlert> kafkaTemplate;

    private final Environment environment;

    public AlertService(KafkaTemplate<String, PoliceWarningAlert> kafkaTemplate, Environment environment) {
        this.environment = environment;
        this.kafkaTemplate = kafkaTemplate;
    }

    private boolean isDevProfileActive() {
        return List.of(environment.getActiveProfiles()).contains("dev");
    }

    public AlertResponse sendMessage(PoliceWarningAlert msg) {
        log.info(String.format("Preparing to send event for topic %s with message %s", topicPoliceWarning, msg));
/*        if (isDevProfileActive()) {
            return AlertResponse.builder().alertId(msg.getId()).msg("Dev profile is active so no alert is sent").build();
        }*/
        // TODO add error handling and remove the send() method. Use @AdviceController in an exception handler
        //kafkaTemplate.send(topicPoliceWarning, msg);
        send(msg);
        return AlertResponse.builder().alertId(msg.getId()).msg("Alert success").build();
    }

    private void send(PoliceWarningAlert message) {
        ListenableFuture<SendResult<String, PoliceWarningAlert>> listenableFuture = kafkaTemplate.send(topicPoliceWarning, message);

        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, PoliceWarningAlert> result) {
                log.info(String.format("Event successfully sent topic %s with result %s", topicPoliceWarning, result));
                log.info(String.format("Event successfully sent with offset data %s", result.getRecordMetadata().offset()));
            }

            @SneakyThrows
            @Override
            public void onFailure(Throwable ex) {
                throw new KafkaPoliceException( ex);
            }
        });
    }
}
