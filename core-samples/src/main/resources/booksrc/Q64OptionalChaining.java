Optional<User> user = findInCache(id)
    .or(() -> findInDatabase(id))
    .or(() -> findInLegacySystem(id));
@@BLOCK@@
// or() の動作フロー
Optional<User> user = findInCache(id)  // ① キャッシュ検索
    .or(() -> findInDatabase(id))  // ② ①が空の場合のみ実行
    .or(() -> findInLegacySystem(id));  // ③ ①②が空の場合のみ実行

// 例: キャッシュにある場合 → ① のみ実行、②③は呼ばれない
// 例: キャッシュになく、DB にある場合 → ①②実行、③は呼ばれない
// 例: すべてにない場合 → ①②③すべて実行、最終結果は Optional.empty()

// ■ or() vs orElse() vs orElseGet() の違い
Optional<String> opt = Optional.of("cached");

// orElse() — 値を直接返す（Optional 解除）、引数は常に評価される
String val1 = opt.orElse(expensiveComputation());
// ↑ opt が値を持っていても expensiveComputation() は実行される！

// orElseGet() — Supplier で遅延評価、値を直接返す（Optional 解除）
String val2 = opt.orElseGet(() -> expensiveComputation());
// ↑ opt が空の場合のみ expensiveComputation() が実行される

// or() — Supplier で遅延評価、Optional を返す（チェーン継続可能）
Optional<String> val3 = opt.or(() -> findAlternative());
// ↑ opt が空の場合のみ findAlternative() が実行される
// ↑ 戻り値は Optional（チェーンを続けられる）

// ■ 実務での使用パターン
Optional<Config> config = loadFromEnvironment()
    .or(() -> loadFromConfigFile())
    .or(() -> loadFromDefaults());

config.ifPresentOrElse(
    cfg -> app.initialize(cfg),
    () -> { throw new IllegalStateException("No configuration found"); }
);