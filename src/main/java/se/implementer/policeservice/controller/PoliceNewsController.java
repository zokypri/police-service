package se.implementer.policeservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.implementer.policeservice.model.PoliceEvent;
import se.implementer.policeservice.service.PoliceService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("v1/police")
public class PoliceNewsController {

    private final PoliceService policeService;

    public PoliceNewsController(PoliceService policeService) {
        this.policeService = policeService;
    }

    @Operation(summary = " Get police news from the Swedish police department")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authentication token provided"),
            @ApiResponse(responseCode = "401", description = "User not authorised",
                    content = @Content)
    })
    @SecurityRequirement(name ="Bearer Auth")
    @GetMapping("/news")
    public List<PoliceEvent> getPoliceNews(@RequestParam(required = false) String city,
                                           @RequestParam(required = false) String date,
                                           @RequestParam(required = false) String type) {
        log.info("Preparing police service news");
        var policeNews = policeService.getPoliceNews(city, date, type);
        log.info("Police news prepared {}", policeNews.size());
        return policeNews;
    }

}
