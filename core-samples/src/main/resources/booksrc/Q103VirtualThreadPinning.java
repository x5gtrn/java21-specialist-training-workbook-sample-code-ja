class InventoryClient {
    synchronized Inventory fetch(String sku) {
        return httpClient.get("/inventory/" + sku);  // 長いブロッキング I/O
    }
}
@@BLOCK@@
class InventoryClient {
    private final Object lock = new Object();
    private final Map<String, Inventory> cache = new HashMap<>();

    Inventory fetch(String sku) {
        Inventory cached;
        synchronized (lock) {
            cached = cache.get(sku);
        }
        if (cached != null) {
            return cached;
        }

        Inventory loaded = httpClient.get("/inventory/" + sku);

        synchronized (lock) {
            cache.put(sku, loaded);
        }
        return loaded;
    }
}
@@BLOCK@@
synchronized (lock) {
    updateSmallSharedState();
}

Result result = blockingCall();

synchronized (lock) {
    storeResult(result);
}