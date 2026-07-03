class SubscriptionService {
    private final Clock clock;

    SubscriptionService(Clock clock) {
        this.clock = clock;
    }

    boolean expired(Subscription subscription) {
        return subscription.expiresAt().isBefore(Instant.now(clock));
    }
}
@@BLOCK@@
@Bean
Clock clock() {
    return Clock.systemUTC();
}
@@BLOCK@@
Clock fixed = Clock.fixed(
    Instant.parse("2026-06-23T00:00:00Z"),
    ZoneOffset.UTC);
@@BLOCK@@
@Test
void expiresAtMidnightUtc() {
    Clock clock = Clock.fixed(
        Instant.parse("2026-01-01T00:00:00Z"),
        ZoneOffset.UTC);
    SubscriptionService service = new SubscriptionService(clock);
    assertThat(service.expired(subscription)).isTrue();
}
@@BLOCK@@
LocalDate today = LocalDate.now(clock.withZone(ZoneId.of("Asia/Tokyo")));
@@BLOCK@@
@ParameterizedTest
@ValueSource(strings = {
    "2024-02-29T23:59:59Z",
    "2026-12-31T23:59:59Z"
})
void handlesDateBoundaries(String instantText) {
    Clock clock = Clock.fixed(Instant.parse(instantText), ZoneOffset.UTC);
    // アサーション
}