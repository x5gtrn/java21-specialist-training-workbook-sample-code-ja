CompletableFuture<Recommendations> future =
        recommendationClient.fetch(userId)
                .orTimeout(500, TimeUnit.MILLISECONDS);
@@BLOCK@@
CompletableFuture<Recommendations> future =
        recommendationClient.fetch(userId)
                .completeOnTimeout(Recommendations.empty(), 500, TimeUnit.MILLISECONDS);
@@BLOCK@@
CompletableFuture<Recommendations> future =
        recommendationClient.fetch(userId)
                .orTimeout(500, TimeUnit.MILLISECONDS)
                .exceptionally(ex -> {
                    log.warn("recommendation fallback for user={}", userId, ex);
                    return Recommendations.empty();
                });
@@BLOCK@@
future.handle((value, error) -> {
    if (error != null) {
        return fallback();
    }
    return value;
});
@@BLOCK@@
CompletableFuture<Recommendations> result =
        client.fetch(userId)
                .orTimeout(500, TimeUnit.MILLISECONDS)
                .exceptionally(ex -> {
                    log.warn("fallback", ex);
                    return Recommendations.empty();
                });
@@BLOCK@@
future.thenApplyAsync(this::expensiveTransform, cpuBoundExecutor)
      .exceptionally(ex -> fallback());