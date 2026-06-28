// 仮想スレッドの作成方法

// 1. Thread.ofVirtual() ビルダー
Thread vt = Thread.ofVirtual().name("worker-1").start(() -> {
    System.out.println("Running on: " + Thread.currentThread());
});

// 2. Executors.newVirtualThreadPerTaskExecutor()（推奨）
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    for (int i = 0; i < 10_000; i++) {
        executor.submit(() -> {
            // 各タスクが独自の仮想スレッドで実行される
            var response = httpClient.send(request, BodyHandlers.ofString());
            processResponse(response);
        });
    }
}  // POINT: try-with-resources で自動的に shutdownAndAwaitTermination

// 3. Thread.startVirtualThread()（簡易版）
Thread.startVirtualThread(() -> {
    System.out.println("Hello from virtual thread!");
});