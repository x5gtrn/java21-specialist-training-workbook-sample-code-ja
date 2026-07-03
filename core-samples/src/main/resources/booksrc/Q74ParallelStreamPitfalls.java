// ■ B: 結合法則を満たさない reduce
List<Integer> nums = List.of(1, 2, 3, 4);

// 逐次: (((0 - 1) - 2) - 3) - 4 = -10（identity 0 から左畳み込み）
int sequential = nums.stream().reduce(0, (a, b) -> a - b);

// 並列: チャンク分割の境界が異なる → 結果が不定
int parallel = nums.parallelStream().reduce(0, (a, b) -> a - b);
// 実行ごとに結果が変わる可能性がある NG:

// OK: 正しい結合法則を満たす演算
int sum = nums.parallelStream().reduce(0, Integer::sum);  // 常に10

// ■ C: 共通 ForkJoinPool の枯渇
// NG: ブロッキング I/O を parallelStream で実行
List<String> urls = /* 1000 URLs */;
urls.parallelStream()
    .map(url -> httpClient.send(request, ofString()))  // POINT: ブロッキング！
    .collect(toList());
// → commonPool のスレッドが HTTP レスポンス待ちで占有
// → 他の parallelStream() も遅くなる

// OK: 解決策1: カスタム ForkJoinPool
ForkJoinPool customPool = new ForkJoinPool(8);
List<String> results = customPool.submit(() ->
    urls.parallelStream()
        .map(url -> httpClient.send(request, ofString()).body())
        .collect(toList())
).get();

// OK: 解決策2: Virtual Threads（Java 21 推奨）
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    List<Future<String>> futures = urls.stream()
        .map(url -> executor.submit(() -> httpClient.send(request, ofString()).body()))
        .toList();
    // 仮想スレッドはブロッキング I/O に最適
}

// ■ データソース別の並列効率
// ArrayList や配列 → 優秀（ランダムアクセス、均等分割が容易）
// HashSet/TreeSet → 普通
// LinkedList     → 劣悪（分割に O(n)、ランダムアクセス不可）
// Stream.iterate → 劣悪（無限ストリーム、分割困難）