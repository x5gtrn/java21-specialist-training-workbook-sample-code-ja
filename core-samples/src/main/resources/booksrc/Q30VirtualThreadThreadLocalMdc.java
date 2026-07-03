// POINT: MDC は `ThreadLocal` ベース
// 仮想スレッド自体は ThreadLocal をサポートするが、
// 以下のケースで問題が発生しうる:

// 1. 非同期処理での MDC 伝播漏れ
CompletableFuture.runAsync(() -> {
    // POINT: 新しいスレッド → MDC は空！
    log.info("Processing...");  // correlationId が欠落
});

// 解決策: MDC コンテキストを手動伝播
Map<String, String> mdcContext = MDC.getCopyOfContextMap();
CompletableFuture.runAsync(() -> {
    MDC.setContextMap(mdcContext);  // POINT: 明示的にコピー
    try {
        log.info("Processing...");  // correlationId が含まれる
    } finally {
        MDC.clear();
    }
});

// 2. TaskDecorator で自動伝播（Spring 推奨パターン）
@Bean
public TaskDecorator mdcTaskDecorator() {
    return runnable -> {
        Map<String, String> context = MDC.getCopyOfContextMap();
        return () -> {
            MDC.setContextMap(context != null ? context : Map.of());
            try { runnable.run(); }
            finally { MDC.clear(); }
        };
    };
}