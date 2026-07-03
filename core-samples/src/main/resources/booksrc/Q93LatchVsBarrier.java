// ■ CountDownLatch: 「N 個の操作完了を待つ」パターン
CountDownLatch latch = new CountDownLatch(3);

// Worker threads (3つ)
for (int i = 0; i < 3; i++) {
    Thread.startVirtualThread(() -> {
        initializeService();
        latch.countDown();  // POINT: 完了を通知（カウント-1）
    });
}

latch.await();  // POINT: メインスレッドが全完了を待機
System.out.println("All services initialized");
// POINT: latch のカウントは0のまま → 再利用不可

// ■ CyclicBarrier: 「全員集合」パターン
CyclicBarrier barrier = new CyclicBarrier(4, () -> {
    System.out.println("Phase complete, starting next phase");  // POINT: バリアアクション
});

for (int i = 0; i < 4; i++) {
    Thread.startVirtualThread(() -> {
        for (int phase = 0; phase < 3; phase++) {
            doPhaseWork(phase);
            barrier.await();  // POINT: 全員がここに到達するまでブロック
            // → 全員到達 → バリアアクション実行 → 自動リセット → 次のフェーズへ
        }
    });
}

// ■ 使い分けの判断基準
// CountDownLatch: 待つ側 ≠ シグナルを送る側（非対称）
// 例: メインが3ワーカーの完了を待つ
// CyclicBarrier: 全員が参加者かつ全員が待つ（対称）
// 例: 4スレッドが全員フェーズ完了後に次フェーズへ