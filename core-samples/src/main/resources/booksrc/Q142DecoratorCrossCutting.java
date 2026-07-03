interface PaymentClient {
    PaymentResult charge(PaymentCommand command);
}

class DefaultPaymentClient implements PaymentClient {
    public PaymentResult charge(PaymentCommand command) {
        // 実際の HTTP 呼び出し
    }
}

class MetricsPaymentClient implements PaymentClient {
    private final PaymentClient delegate;
    private final MeterRegistry registry;

    public PaymentResult charge(PaymentCommand command) {
        Timer.Sample sample = Timer.start(registry);
        try {
            return delegate.charge(command);
        } finally {
            sample.stop(registry.timer("payment.charge"));
        }
    }
}
@@BLOCK@@
PaymentClient client =
    new MetricsPaymentClient(
        new RetryingPaymentClient(
            new DefaultPaymentClient(httpClient)));
@@BLOCK@@
@Bean
PaymentClient paymentClient(HttpClient httpClient, MeterRegistry registry) {
    PaymentClient core = new DefaultPaymentClient(httpClient);
    PaymentClient retry = new RetryingPaymentClient(core);
    return new MetricsPaymentClient(retry, registry);
}