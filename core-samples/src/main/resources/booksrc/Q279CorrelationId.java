ProducerRecord<String, OrderEvent> record =
    new ProducerRecord<>("orders", event);
record.headers().add("X-Correlation-Id", correlationId.getBytes(UTF_8));
producer.send(record);
@@BLOCK@@
String correlationId = header(record, "X-Correlation-Id");
try (MDC.MDCCloseable ignored = MDC.putCloseable("correlationId", correlationId)) {
    handle(record.value());
}
@@BLOCK@@
@KafkaListener(topics = "orders")
void consume(ConsumerRecord<String, OrderEvent> record) {
    String correlationId = readHeader(record.headers(), "X-Correlation-Id");
    try (var ignored = MDC.putCloseable("correlationId", correlationId)) {
        log.info("processing order event orderId={}", record.value().orderId());
        service.handle(record.value());
    }
}