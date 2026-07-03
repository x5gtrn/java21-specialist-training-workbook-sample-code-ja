// 1. catch 句 — 例外オブジェクトが不要な場合
try {
    riskyOperation();
} catch (IOException _) {  // POINT: 例外オブジェクトを使わない
    log.warn("I/O error occurred");
}

// 2. ラムダ — 一部のパラメータが不要な場合
map.forEach((_, value) -> process(value));  // POINT: キーは不要
BiFunction<String, Integer, String> f = (_, count) -> "Count: " + count;

// 3. switch パターン — 型だけ判定、変数は不要
String describe(Shape shape) {
    return switch (shape) {
        case Circle _    -> "It's a circle";  // POINT: radius 不要
        case Rectangle _ -> "It's a rectangle";  // POINT: width/height 不要
    };
}

// 4. 拡張 for 文 — 要素は不要、回数だけ必要な場合
int count = 0;
for (var _ : collection) { count++; }  // POINT: 要素自体は不要

// NG: 参照しようとするとコンパイルエラー
catch (IOException _) {
    log.error(_.getMessage());  // NG: コンパイルエラー: _ は参照不可
}