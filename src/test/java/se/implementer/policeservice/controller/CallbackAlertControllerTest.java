package se.implementer.policeservice.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import se.implementer.policeservice.exception.KafkaPoliceException;
import se.implementer.policeservice.model.AlertResponse;
import se.implementer.policeservice.service.AlertService;

@WebMvcTest(controllers = CallbackAlertController.class)
@ActiveProfiles("test")
public class CallbackAlertControllerTest {

    @MockBean
    AlertService alertService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnSuccess() throws Exception {
        when(alertService.sendMessage(any())).thenReturn(AlertResponse.builder().alertId(1).msg("Alert success").build());

        String filePathRequest = "__files/AlertWarning.json";
        String filePathResponse = "__files/AlertResponse.json";

        String requestContent = Files.readString(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(filePathRequest)).toURI()));
        String responseContent = Files.readString(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(filePathResponse)).toURI()));

        mockMvc.perform(
                post("/v1/callback/alert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(content().json(responseContent))
                .andExpect(status().isOk());
    }

    @Test
    void shouldThrowExceptionWhenKafkaFails() throws Exception {
        when(alertService.sendMessage(any())).thenThrow(new KafkaPoliceException(new RuntimeException()));

        String filePathRequest = "__files/AlertWarning.json";

        String requestContent = Files.readString(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(filePathRequest)).toURI()));

        mockMvc.perform(
                        post("/v1/callback/alert")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestContent))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldThrowValidationExceptionWhenIdIsNegative() throws Exception {

        String filePathRequest = "__files/ValidationErrorNegativeIdAlertWarning.json";

        String requestContent = Files.readString(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(filePathRequest)).toURI()));

        mockMvc.perform(
                        post("/v1/callback/alert")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestContent))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("'policeWarningAlert' on field 'id': rejected value [-1]")));
    }



    @Test
    void shouldThrowValidationExceptionWhenMsgIsBlank() throws Exception {

        String filePathRequest = "__files/ValidationErrorBlankMsgAlertWarning.json";

        String requestContent = Files.readString(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(filePathRequest)).toURI()));

        mockMvc.perform(
                        post("/v1/callback/alert")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestContent))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("Alert message is mandatory")));
    }

    @Test
    void shouldThrowWhenValidationErrorMsgIsNullA() throws Exception {

        String filePathRequest = "__files/ValidationErrorMsgIsNullAlertWarning.json";

        String requestContent = Files.readString(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(filePathRequest)).toURI()));

        mockMvc.perform(
                        post("/v1/callback/alert")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestContent))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("NotNull.policeWarningAlert.msg")));
    }
}
