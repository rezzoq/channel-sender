package notificator.models;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "api_keys")
public class ApiKey {
    @Id
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "key_hash", nullable = false, unique = true)
    private String keyHash;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "revoked_at")
    private Instant revokedAt;

    protected ApiKey() {}

    public ApiKey(UUID tenantId, String keyHash) {
        this.id = UUID.randomUUID();
        this.tenantId = tenantId;
        this.keyHash = keyHash;
        this.createdAt = Instant.now();
    }
}