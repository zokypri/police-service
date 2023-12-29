package se.implementer.policeservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.implementer.policeservice.service.AlertService;
import se.implementer.policeservice.model.AlertResponse;
import se.implementer.policeservice.model.kafka.PoliceWarningAlert;

@Slf4j
@RestController
@RequestMapping("v1/callback")
public class CallbackAlertController {

    private final AlertService alertService;

    public CallbackAlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @Operation(summary = " Get authentication token for registered user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authentication token provided"),
            @ApiResponse(responseCode = "401", description = "User not authorised",
                    content = @Content)
    })
    @SecurityRequirement(name ="Bearer Auth")
    @PostMapping("/alert")
    public AlertResponse policeAlert(@RequestBody PoliceWarningAlert policeWarningAlert) {
        return alertService.sendMessage(policeWarningAlert);
    }
}
