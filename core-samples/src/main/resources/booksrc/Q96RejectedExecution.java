// ■ 問題の構成
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    10, 10,  // コア/最大 = 10スレッド
    60L, TimeUnit.SECONDS,
    new ArrayBlockingQueue<>(100),  // POINT: 制限付きキュー（100タスク）
    new ThreadPoolExecutor.AbortPolicy()  // POINT: キュー満杯 → RejectedExecutionException
);

// ■ 問題の連鎖
// 10スレッドすべてが SELECT ... (5秒の DB クエリ) でブロック
// → キューに新タスクが蓄積 → 100件で満杯
// → AbortPolicy → RejectedExecutionException

// ■ 緩和策1: タイムアウトの設定（次の2つは別物）
// connection-timeout: プールからの接続取得待ちの上限（クエリ実行時間ではない）
// spring.datasource.hikari.connection-timeout=5000
// setQueryTimeout: クエリ実行時間の上限 ← 今回の遅いクエリに効くのはこちら
// statement.setQueryTimeout(3);  // 3秒でクエリ中断

// ■ 緩和策2: CallerRunsPolicy（呼び出し元スレッドで実行）
new ThreadPoolExecutor(10, 10, 60L, TimeUnit.SECONDS,
    new ArrayBlockingQueue<>(100),
    new ThreadPoolExecutor.CallerRunsPolicy()  // POINT: 拒否せず、呼び出し元で実行
);
// → 呼び出し元スレッドがタスクを実行 → 自然なバックプレッシャー

// ■ 緩和策3: Virtual Threads（Java 21 推奨）
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    for (Task task : tasks) {
        executor.submit(task::execute);
        // POINT: 仮想スレッドは I/O 待ちでキャリアスレッドを解放
        // → スレッド枯渇問題が根本的に解消
    }
}