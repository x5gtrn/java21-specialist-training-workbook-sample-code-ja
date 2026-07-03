try (var executor = Executors.newFixedThreadPool(
        Runtime.getRuntime().availableProcessors())) {
    // CPU バウンド処理はコア数程度の並列度に制限する
}
@@BLOCK@@
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    urls.forEach(url -> executor.submit(() -> httpClient.send(request(url), handler)));
}
@@BLOCK@@
int parallelism = Runtime.getRuntime().availableProcessors();
ExecutorService cpuPool = Executors.newFixedThreadPool(parallelism);
@@BLOCK@@
// I/O 待ちは仮想スレッドで扱いやすい
Image image = imageClient.fetch(id);

// CPU 処理は並列度を制御した pool へ
Future<Thumbnail> thumbnail =
        cpuPool.submit(() -> resize(image));