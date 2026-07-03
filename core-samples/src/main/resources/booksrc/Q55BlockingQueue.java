// BlockingQueue の主要メソッド比較
BlockingQueue<Event> queue = new LinkedBlockingQueue<>(100);  // 容量100

// ■ 挿入操作（キューが満杯の場合の動作の違い）
queue.put(event);  // POINT: ブロック（空くまで待機）
queue.offer(event);  // false を返す（ブロックしない）
queue.offer(event, 5, SECONDS);  // 5秒待ってもダメなら false
queue.add(event);  // IllegalStateException をスロー

// ■ 取り出し操作（キューが空の場合の動作の違い）
Event e = queue.take();  // POINT: ブロック（要素が来るまで待機）
Event e = queue.poll();  // null を返す（ブロックしない）
Event e = queue.poll(5, SECONDS);  // 5秒待ってもダメなら null

// ■ プロデューサー・コンシューマーパターン
// Producer（提供側）
Thread producer = Thread.ofVirtual().start(() -> {
    while (running) {
        Event event = generateEvent();
        queue.put(event);  // キュー満杯ならブロック（バックプレッシャー）
    }
});

// Consumer（受け取り側）
Thread consumer = Thread.ofVirtual().start(() -> {
    while (running) {
        Event event = queue.take();  // キュー空ならブロック
        processEvent(event);
    }
});

// ■ 主な BlockingQueue 実装
// LinkedBlockingQueue — リンクリストベース、容量は構築時に指定（省略時 Integer.MAX_VALUE で実質無制限）
// ArrayBlockingQueue  — 配列ベース、固定容量、フェアネスオプション
// PriorityBlockingQueue — 優先度付き、無制限
// SynchronousQueue    — 容量0、直接ハンドオフ