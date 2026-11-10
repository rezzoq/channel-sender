package notificator.controller;

import notificator.service.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/tenants")
public class TenantController {
    private final TenantService service;

    public TenantController(TenantService service) {
        this.service = service;
    }

    public record CreateTenantRequest(String name) {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TenantService.CreatedTenant create(@RequestBody CreateTenantRequest request) {
        if (request.name() == null || request.name().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "name is required");
        }
        return service.create(request.name().trim());
    }
}