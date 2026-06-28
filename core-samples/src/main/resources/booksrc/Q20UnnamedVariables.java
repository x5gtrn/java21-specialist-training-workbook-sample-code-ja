// 局所変数 / 拡張 for / try-with-resources
var _ = queue.poll();                  // 戻り値を捨てる
for (var _ : items) { count++; }       // 要素自体は不要
try (var _ = openTracing()) { run(); } // クローズだけ必要

// catch / ラムダ / レコードパターン
try { parse(s); } catch (NumberFormatException _) { fallback(); }
map.forEach((key, _) -> seen.add(key));     // 値は不要
if (obj instanceof Point(int x, _)) { use(x); }  // y は不要
@@BLOCK@@
var _ = first();
var _ = second();   // 通常変数なら重複定義エラー。無名変数なら OK