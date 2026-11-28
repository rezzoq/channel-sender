package notificator.models;

import jakarta.persistence.*;
import notificator.common.Channel;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notifications")
public class Notification {
    public enum Status { PENDING, SENT, FAILED }

    @Id
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Channel channel;

    @Column(nullable = false)
    private String target;

    @Column(nullable = false)
    private String payload;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected Notification() {}

    public Notification(UUID tenantId, Channel channel, String target, String payload) {
        this.id = UUID.randomUUID();
        this.tenantId = tenantId;
        this.channel = channel;
        this.target = target;
        this.payload = payload;
        this.status = Status.PENDING;
        this.createdAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getTenantId() { return tenantId; }
    public Channel getChannel() { return channel; }
    public String getTarget() { return target; }
    public String getPayload() { return payload; }
    public Status getStatus() { return status; }
}