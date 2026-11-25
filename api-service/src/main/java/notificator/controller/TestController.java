package notificator.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
public class TestController {
    @GetMapping("/whoami")
    public Map<String, UUID> whoAmI(@AuthenticationPrincipal UUID tenantId) {
        return Map.of("tenantId", tenantId);
    }
}