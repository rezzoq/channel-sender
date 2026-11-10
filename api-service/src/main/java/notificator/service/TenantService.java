package notificator.service;

import notificator.models.ApiKey;
import notificator.models.Tenant;
import notificator.repository.ApiKeyRepository;
import notificator.repository.TenantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HexFormat;
import java.util.UUID;

@Service
public class TenantService {
    private static final SecureRandom RANDOM = new SecureRandom();

    private final TenantRepository tenants;
    private final ApiKeyRepository apiKeys;

    public TenantService(TenantRepository tenants, ApiKeyRepository apiKeys) {
        this.tenants = tenants;
        this.apiKeys = apiKeys;
    }

    public record CreatedTenant(UUID tenantId, String apiKey) {}

    @Transactional
    public CreatedTenant create(String name) {
        if (tenants.existsByName(name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tenant already exists");
        }
        Tenant tenant = tenants.save(new Tenant(name));

        String rawKey = generateKey();
        apiKeys.save(new ApiKey(tenant.getId(), sha256(rawKey)));

        return new CreatedTenant(tenant.getId(), rawKey);
    }

    private static String generateKey() {
        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);
        return "cs_" + Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private static String sha256(String value) {
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256")
                    .digest(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}