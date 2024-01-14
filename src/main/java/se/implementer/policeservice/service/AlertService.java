package se.implementer.policeservice.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    public AlertService(KafkaTemplate<String, PoliceWarningAlert> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public AlertResponse sendMessage(PoliceWarningAlert msg) {
        log.info(String.format("Preparing to send event for topic %s with message %s", topicPoliceWarning, msg));
        ListenableFuture<SendResult<String, PoliceWarningAlert>> listenableFuture = kafkaTemplate.send(topicPoliceWarning, msg);
        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, PoliceWarningAlert> result) {
                log.info(String.format("Event successfully sent topic %s with result %s", topicPoliceWarning, result));
            }
            @SneakyThrows
            @Override
            public void onFailure(Throwable ex) {
                throw new KafkaPoliceException( ex);
            }
        });
        return AlertResponse.builder().alertId(msg.getId()).msg("Alert success").build();
    }
}
