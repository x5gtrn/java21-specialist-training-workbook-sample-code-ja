// カスタム Collector の実装例: 売上サマリー
record SalesSummary(long count, double totalRevenue, double averageValue) {}

Collector<Transaction, double[], SalesSummary> salesCollector = Collector.of(
    // 1. supplier: 結果コンテナ作成 [count, totalRevenue]
    () -> new double[2],

    // 2. accumulator: 各要素を蓄積
    (container, txn) -> {
        container[0] += 1;  // count++
        container[1] += txn.amount();  // total += amount
    },

    // 3. combiner: 並列時に2つのコンテナをマージ
    (left, right) -> {
        left[0] += right[0];  // count 合算
        left[1] += right[1];  // total 合算
        return left;
    },

    // 4. finisher: 中間コンテナ → 最終出力型
    container -> new SalesSummary(
        (long) container[0],
        container[1],
        container[0] > 0 ? container[1] / container[0] : 0.0
    )
);

SalesSummary summary = transactions.stream().collect(salesCollector);
// SalesSummary[count=1500, totalRevenue=2850000.0, averageValue=1900.0]

// POINT: 3パラメータ版（finisher 省略 = IDENTITY_FINISH）
// コンテナ型と出力型が同じ場合は finisher 不要
Collector<String, StringJoiner, StringJoiner> joinerCollector = Collector.of(
    () -> new StringJoiner(", "),  // supplier
    StringJoiner::add,  // accumulator
    StringJoiner::merge  // combiner
    // finisher 省略 → コンテナ(StringJoiner)がそのまま出力
);

// POINT: 並列ストリームでの combiner 動作
List<Transaction> bigData = /* 1,000,000 transactions */;
SalesSummary parallelResult = bigData.parallelStream().collect(salesCollector);
// Thread-1: [500, 950000] ←→ Thread-2: [500, 900000]
// ↓ combiner
// [1000, 1850000]
// ←→ Thread-3: [500, 1000000]
// ↓ combiner
// [1500, 2850000] → finisher → SalesSummary