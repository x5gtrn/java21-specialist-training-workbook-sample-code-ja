class ResponseCache {
    private final ConcurrentHashMap<Key, CachedResponse> cache = new ConcurrentHashMap<>();
    private final int maxEntries = 10_000;
}
@@BLOCK@@
record CachedResponse(String body, RequestContext context) {}
@@BLOCK@@
record CachedResponse(String body, String contentType, Instant createdAt) {}
@@BLOCK@@
record CachedResponse(String body, String contentType, Instant createdAt) {}

boolean isExpired(CachedResponse response, Clock clock) {
    return response.createdAt().plus(Duration.ofMinutes(10)).isBefore(clock.instant());
}
@@BLOCK@@
record SearchCacheKey(
        String tenantId,
        String query,
        int page,
        String sort
) {}
@@BLOCK@@
// 避けたい: 不要な文脈まで保持する
record BadCachedResponse(String body, RequestContext context) {}

// よい: キャッシュに必要な値だけ保持する
record GoodCachedResponse(String body, String contentType, Instant createdAt) {}