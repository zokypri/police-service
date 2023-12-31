package se.implementer.policeservice.component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.apache.kafka.clients.consumer.Consumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@EmbeddedKafka
public class AlertWarningKafkaTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.producer.topic.policeWarning}")
    private String topic;

    private Consumer<String, String> consumer;

    @Test
    void shouldPublishKafkaEvent() throws Exception {

        {
            String filePathRequest = "__files/AlertWarning.json";
            String filePathResponse = "__files/AlertResponse.json";

            // Read content from file
            String requestContent = Files.readString(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(filePathRequest)).toURI()));
            String responseContent = Files.readString(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(filePathResponse)).toURI()));

            // Perform the test using the content from the file
            mockMvc.perform(MockMvcRequestBuilders.post("/v1/callback/alert")
                            .contentType("application/json")
                            .content(requestContent)) // Use content from the file
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json(responseContent)); // Assert content using json()

        }
    }
}
