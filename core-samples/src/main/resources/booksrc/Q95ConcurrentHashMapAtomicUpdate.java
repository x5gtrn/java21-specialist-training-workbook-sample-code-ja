ConcurrentHashMap<String, Integer> wordCount = new ConcurrentHashMap<>();

// A: merge — 最も簡潔で推奨される方法（アトミック）
wordCount.merge("hello", 1, Integer::sum);
// "hello" が無ければ 1 を設定 / あれば 3 → 3 + 1 = 4 に更新

// 別解: compute も同様にアトミックだが、null 判定が必要で冗長
wordCount.compute("hello", (key, val) -> val == null ? 1 : val + 1);

// B: NG: 非アトミック — get と put の間にレースコンディション
wordCount.put("hello", wordCount.get("hello") + 1);
// Thread1: get → 3, Thread2: get → 3
// Thread1: put(4), Thread2: put(4) ← 1回分の加算が失われる

// C: NG: 非アトミック — containsKey → get → put の境界で他スレッドが割り込む
if (wordCount.containsKey("hello")) {
    wordCount.put("hello", wordCount.get("hello") + 1);
} else {
    wordCount.put("hello", 1);
}

// E: NG: 非アトミック — putIfAbsent と replace は別々のアトミック操作
wordCount.putIfAbsent("hello", 0);
wordCount.replace("hello", wordCount.get("hello") + 1);
// 2つの操作の間に他のスレッドが割り込む可能性