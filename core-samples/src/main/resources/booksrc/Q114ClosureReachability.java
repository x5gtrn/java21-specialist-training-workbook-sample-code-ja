// ■ クロージャによる意図しないオブジェクト保持の例
public Supplier<String> createSupplier() {
    byte[] largeData = loadLargeDataset();  // POINT: 100MB のデータ

    Supplier<String> supplier = () -> processData(largeData);
    // POINT: ラムダが largeData への参照をキャプチャ（クロージャ）

    return supplier;
    // → supplier が生存している限り、largeData (100MB) も GC されない
    // → createSupplier() のローカルスコープを抜けても largeData は到達可能
}

// ■ 対策1: キャプチャを最小化（必要なデータだけ抽出）
public Supplier<String> createSupplierFixed() {
    byte[] largeData = loadLargeDataset();  // 100MB
    String summary = extractSummary(largeData);  // POINT: 必要な情報だけ抽出（数 KB）

    return () -> formatReport(summary);  // POINT: summary のみキャプチャ
    // → largeData はメソッド終了後に GC 対象に
}

// ■ 対策2: ストリームパイプラインでの注意
List<Predicate<String>> filters = new ArrayList<>();
for (ExpensiveConfig config : configs) {
    // NG: 各ラムダが config オブジェクト全体をキャプチャ
    filters.add(s -> s.matches(config.getPattern()));
    // → configs の全要素がフィルタリスト経由で到達可能に
}

// OK: 必要な値のみキャプチャ
for (ExpensiveConfig config : configs) {
    String pattern = config.getPattern();  // POINT: 必要な値だけ変数に抽出
    filters.add(s -> s.matches(pattern));  // POINT: pattern（String）のみキャプチャ
    // → config 本体は GC 可能に
}

// ■ エスケープ解析との関係（選択肢 B の誤り）
// エスケープ解析はヒープ割り当てを「削減」する最適化:
// → オブジェクトがメソッド外に「エスケープ」しない場合、スタック割り当てやスカラー置換が可能
// → ラムダにキャプチャされたオブジェクトは「エスケープ」するため、
// エスケープ解析による最適化の対象外になることが多い
// POINT: エスケープ解析は「Old 世代への昇格を促進」するものではない（逆の効果）