// POINT: AtomicLong: CAS ベース（ロックフリー）
AtomicLong requestCounter = new AtomicLong(0);

// incrementAndGet() の内部擬似コード:
// long increment() {
// while (true) {  // CAS loop (spin)
// long current = value;          // 1. 現在値を読み取り
// long next = current + 1;       // 2. 新値を計算
// if (CAS(current, next))        // 3. CAS: 成功すれば next に更新
// return next;               //    失敗（他スレッドが先に更新）→ リトライ
// }
// }

// POINT: synchronized: モニタロックベース
long count = 0;
synchronized (this) {  // 1. モニタ取得（競合時はブロック）
    count++;  // 2. 操作
}  // 3. モニタ解放

// ■ パフォーマンス比較（概念的）
// 低競合: AtomicLong ≈ synchronized（差は小さい）
// 中競合: AtomicLong >> synchronized（ロックの待ち時間が差を拡大）
// 高競合: AtomicLong の CAS リトライが増加、LongAdder を検討

// ■ 高競合向け: LongAdder（Java 8+）
LongAdder adder = new LongAdder();
adder.increment();  // POINT: 内部でセル分割して CAS 競合を分散
long total = adder.sum();  // 全セルの合計を取得
// → AtomicLong より高競合で高スループット（ただし sum() のコストあり）