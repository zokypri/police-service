package se.implementer.policeservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.implementer.policeservice.model.PoliceEvent;
import se.implementer.policeservice.service.PoliceService;

import java.util.List;

@RestController
@RequestMapping("v1/police")
public class PoliceNewsController {

    private final PoliceService policeService;

    public PoliceNewsController(PoliceService policeService) {
        this.policeService = policeService;
    }

    @Operation(summary = " Get authentication token for registered user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authentication token provided"),
            @ApiResponse(responseCode = "401", description = "User not authorised",
                    content = @Content)
    })
    @SecurityRequirement(name ="Bearer Auth")
    @GetMapping("/news")
    public List<PoliceEvent> getPoliceNews() {
        return policeService.getPoliceNews();
    }

}
