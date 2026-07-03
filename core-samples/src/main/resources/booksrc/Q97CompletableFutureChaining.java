CompletableFuture<String> result = CompletableFuture
    .supplyAsync(() -> fetchProduct("SKU-001"))
    .thenApply(product -> calculatePrice(product))
    .thenApply(price -> String.format("Total: $%.2f", price))
    .exceptionally(ex -> "Error: " + ex.getMessage());
@@BLOCK@@
// 正常系の処理フロー
// supplyAsync → thenApply(calcPrice) → thenApply(format) → 結果: "Total: $29.99"

// 異常系の処理フロー（fetchProduct が例外をスロー）
// supplyAsync → 例外発生 → thenApply(calcPrice) スキップ → thenApply(format) スキップ
// → exceptionally → 結果: "Error: Product not found"

// thenApply vs thenApplyAsync
CompletableFuture<String> sync = cf.thenApply(x -> transform(x));
// ↑ 完了スレッドで同期実行

CompletableFuture<String> async = cf.thenApplyAsync(x -> transform(x));
// ↑ ForkJoinPool.commonPool() の別スレッドで非同期実行

CompletableFuture<String> asyncCustom = cf.thenApplyAsync(x -> transform(x), customExecutor);
// ↑ カスタム Executor の別スレッドで非同期実行