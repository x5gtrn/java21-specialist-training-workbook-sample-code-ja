// 悪い例: 機密情報と大量 payload
System.out.println("request=" + requestBody + ", token=" + token);

// よい例: 構造化、目的限定、redaction
log.info("payment authorization failed",
    kv("correlation_id", correlationId),
    kv("provider", "stripe"),
    kv("tenant_id", tenantId),
    kv("status", status),
    kv("reason_code", reasonCode));