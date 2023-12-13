package se.implementer.policeservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import se.implementer.policeservice.service.PoliceService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static utils.TestData.createPoliceEvents;

@WebMvcTest
public class PoliceNewsControllerTest {

    @MockBean
    PoliceService policeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnPoliceEvent() throws Exception {

        when(policeService.getPoliceNews(any(), any(), any()))
                .thenReturn(createPoliceEvents());

        mockMvc.perform(get("/v1/police/news"))
                .andExpect(content().json("""
                        [
                          {
                            "id": 1223,
                            "name": "desc",
                            "url": "https://polisen.se/url",
                            "type": "Brand",
                            "location": {
                                  "name": "Solna"
                                }
                          },
                          {
                            "id": 1224,
                            "name": "desc",
                            "url": "https://polisen.se/url",
                            "type": "Rattfylleri",
                            "location": {
                                  "name": "Skellefteå"
                                }
                          }
                        ]
                        """))
                .andExpect(status().isOk());
    }
    @Test
    void shouldReturnEmptyListOfPoliceEvent() throws Exception {

        when(policeService.getPoliceNews(anyString(), anyString(), anyString()))
                .thenReturn(List.of());

        mockMvc.perform(get("/v1/police/news"))
                .andExpect(content().json("[]"))
                .andExpect(status().isOk());
    }

}
