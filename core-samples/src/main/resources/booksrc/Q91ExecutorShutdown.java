// NG: shutdown() 忘れ → JVM が終了しない
public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(4);
    executor.submit(() -> System.out.println("Task done"));
    System.out.println("Main finished");
    // POINT: main() は終了するが、4つのワーカースレッド（非デーモン）が生存
    // → JVM は終了しない → プロセスがゾンビ化
}

// OK: 修正1: try-with-resources（Java 19+ で利用可能）
public static void main(String[] args) {
    try (var executor = Executors.newFixedThreadPool(4)) {
        executor.submit(() -> System.out.println("Task done"));
    }  // POINT: close() → shutdown() + awaitTermination() 自動呼び出し
    // → ワーカースレッド停止 → JVM 正常終了
}

// OK: 修正2: 明示的なシャットダウンシーケンス
ExecutorService executor = Executors.newFixedThreadPool(4);
try {
    executor.submit(() -> processTask());
} finally {
    executor.shutdown();  // POINT: 新規タスク受付停止 + 既存タスク完了待ち
    if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
        List<Runnable> unfinished = executor.shutdownNow();  // POINT: 割り込み強制停止
        log.warn("{} tasks were not completed", unfinished.size());
    }
}

// ■ デーモン vs 非デーモンスレッド
// 非デーモン（user threads）: JVM は全非デーモンスレッド終了まで待機
// デーモン（daemon threads）: JVM は待たずに終了（バックグラウンド処理用）
// Thread t = new Thread(task);
// t.setDaemon(true);  // デーモンスレッドに設定（start 前に呼ぶ必要あり）

// ■ デーモンスレッドを使う Executor の作成
ExecutorService daemonExecutor = Executors.newFixedThreadPool(4, r -> {
    Thread t = new Thread(r);
    t.setDaemon(true);  // POINT: デーモンスレッドに設定
    return t;
});
// → main 終了後に JVM が自動終了（ただしタスクが途中で強制終了されるリスク）