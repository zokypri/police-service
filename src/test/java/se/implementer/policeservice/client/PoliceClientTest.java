package se.implementer.policeservice.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(PoliceClient.class)
@AutoConfigureWebClient(registerRestTemplate = true)
public class PoliceClientTest {

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private PoliceClient policeClient;

    @Test
    void shouldReturnEventFromSolna() {

        server.expect(requestTo("https://polisen.se/api/events?locationname=Solna"))
                .andRespond(withSuccess(resourceLoader.getResource("classpath:__files/EventSolna.json"),  MediaType.APPLICATION_JSON));
        var response = policeClient.getPoliceNews("Solna", null, null);

        assertThat(response).isNotNull();
        assertEquals(response.size(), 1);
        assertEquals(response.get(0).getLocation().getCity(), "Solna");
        assertEquals(response.get(0).getId(), 375940);
    }

}
