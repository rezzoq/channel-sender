package notificator.service;

import notificator.common.Channel;
import notificator.common.NotificationEvent;
import notificator.models.Notification;
import notificator.repository.NotificationRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationService {
    public static final String TOPIC = "notifications";

    private final NotificationRepository repository;
    private final KafkaTemplate<String, NotificationEvent> kafka;

    public NotificationService(NotificationRepository repository,
                               KafkaTemplate<String, NotificationEvent> kafka) {
        this.repository = repository;
        this.kafka = kafka;
    }

    public record Accepted(UUID id, Notification.Status status) {}

    public Accepted create(UUID tenantId, Channel channel, String target, String payload) {
        Notification n = repository.save(new Notification(tenantId, channel, target, payload));
        kafka.send(TOPIC, tenantId.toString(),
                new NotificationEvent(n.getId(), tenantId, channel, target, payload));

        return new Accepted(n.getId(), n.getStatus());
    }
}