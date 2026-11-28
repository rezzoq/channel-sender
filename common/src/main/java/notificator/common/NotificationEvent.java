package notificator.common;

import java.util.UUID;

public record NotificationEvent(
        UUID notificationId,
        UUID tenantId,
        Channel channel,
        String target,
        String payload) {}