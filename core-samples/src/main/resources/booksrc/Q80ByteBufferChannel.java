// ByteBuffer のライフサイクル
ByteBuffer buffer = ByteBuffer.allocate(10);
// 初期状態: position=0, limit=10, capacity=10

// 1. 書き込み
buffer.put((byte) 'H');
buffer.put((byte) 'i');
// 状態: position=2, limit=10, capacity=10
// 内容: [H][i][ ][ ][ ][ ][ ][ ][ ][ ]
// ↑pos                  ↑limit

// 2. flip() — 読み取り準備
buffer.flip();
// 状態: position=0, limit=2, capacity=10
// 内容: [H][i][ ][ ][ ][ ][ ][ ][ ][ ]
// ↑pos  ↑limit
// → 位置0から limit(2)まで読み取り可能

// 3. 読み取り
byte b1 = buffer.get();  // 'H', position=1
byte b2 = buffer.get();  // 'i', position=2

// 4. clear() — 再書き込み準備
buffer.clear();
// 状態: position=0, limit=10, capacity=10
// ※データは消えない（論理的にリセットされるだけ）

// 5. compact() — 未読データを先頭に移動
buffer.compact();
// 読み取られていないデータを先頭に詰めて、残りスペースに書き込み可能に