HttpRequest request = HttpRequest.newBuilder()
    .uri(URI.create("https://partner.example.com/payments"))
    .timeout(Duration.ofSeconds(3))
    .header("Idempotency-Key", idempotencyKey)
    .POST(HttpRequest.BodyPublishers.ofString(json))
    .build();