final class LeakyCache {
    private static final Map<String, CustomerDto> CACHE = new ConcurrentHashMap<>();

    static CustomerDto load(String id) {
        return CACHE.computeIfAbsent(id, LeakyCache::fetch);
    }
}