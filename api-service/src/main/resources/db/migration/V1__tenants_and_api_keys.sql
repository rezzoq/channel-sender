CREATE TABLE tenants (
 id UUID PRIMARY KEY,
 name VARCHAR(255) NOT NULL UNIQUE,
 created_at TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE TABLE api_keys (
 id UUID PRIMARY KEY,
 tenant_id  UUID         NOT NULL REFERENCES tenants (id),
 key_hash   VARCHAR(64)  NOT NULL UNIQUE,
 created_at TIMESTAMPTZ  NOT NULL DEFAULT now(),
 revoked_at TIMESTAMPTZ
);

CREATE INDEX idx_api_keys_tenant ON api_keys (tenant_id);