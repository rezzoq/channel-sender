CREATE TABLE notifications (
   id UUID PRIMARY KEY,
   tenant_id  UUID NOT NULL REFERENCES tenants (id),
   channel VARCHAR(20) NOT NULL,
   target VARCHAR(1000) NOT NULL,
   payload TEXT NOT NULL,
   status VARCHAR(20) NOT NULL,
   created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_notifications_tenant ON notifications (tenant_id, created_at DESC);