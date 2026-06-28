List<Integer> timestamps = List.of(100, 200, 300, 400, 500, 600);
List<Integer> result = timestamps.stream()
    .dropWhile(t -> t < 300)
    .takeWhile(t -> t <= 500)
    .toList();
@@BLOCK@@
// ステップバイステップの処理
// 元のストリーム: [100, 200, 300, 400, 500, 600]

// dropWhile(t -> t < 300):
// 100 → t < 300 = true → 破棄
// 200 → t < 300 = true → 破棄
// 300 → t < 300 = false → ここから通過開始
// → [300, 400, 500, 600]

// takeWhile(t -> t <= 500):
// 300 → t <= 500 = true → 取得
// 400 → t <= 500 = true → 取得
// 500 → t <= 500 = true → 取得
// 600 → t <= 500 = false → ここで停止
// → [300, 400, 500]