int threads = 100;
CountDownLatch start = new CountDownLatch(1);
ExecutorService executor = Executors.newFixedThreadPool(threads);

for (int i = 0; i < threads; i++) {
    executor.submit(() -> {
        start.await();
        cache.increment("key");
        return null;
    });
}
start.countDown();
@@BLOCK@@
assertThat(cache.get("key")).isEqualTo(100);
@@BLOCK@@
@RepeatedTest(100)
void incrementsAreNotLost() throws Exception {
    int n = 100;
    ExecutorService executor = Executors.newFixedThreadPool(16);
    CountDownLatch start = new CountDownLatch(1);
    List<Future<?>> futures = IntStream.range(0, n)
        .mapToObj(i -> executor.submit(() -> {
            start.await();
            counter.increment();
            return null;
        }))
        .toList();
    start.countDown();
    for (Future<?> f : futures) f.get(1, TimeUnit.SECONDS);
    assertThat(counter.value()).isEqualTo(n);
}
@@BLOCK@@
cache.compute(key, (k, old) -> old == null ? 1 : old + 1);