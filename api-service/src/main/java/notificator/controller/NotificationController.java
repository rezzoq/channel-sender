package notificator.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import notificator.common.Channel;
import notificator.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    public record CreateNotificationRequest(
            @NotNull Channel channel,
            @NotBlank String target,
            @NotBlank String payload) {}

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public NotificationService.Accepted create(@AuthenticationPrincipal UUID tenantId,
                                               @Valid @RequestBody CreateNotificationRequest request) {
        return service.create(tenantId, request.channel(), request.target(), request.payload());
    }
}