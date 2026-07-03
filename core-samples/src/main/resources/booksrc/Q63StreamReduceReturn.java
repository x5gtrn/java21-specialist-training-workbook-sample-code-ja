// ■ identity なし → Optional<T>
Optional<Integer> result1 = Stream.of("Java", "Scala", "Rust")
    .map(String::length)
    .reduce(Integer::sum);
// result1 = Optional[13]  (4 + 5 + 4)

Optional<Integer> result2 = Stream.<String>empty()
    .map(String::length)
    .reduce(Integer::sum);
// result2 = Optional.empty()  POINT: 空ストリーム → 結果なし

// ■ identity あり → T（Optional 不要）
int result3 = Stream.of("Java", "Scala", "Rust")
    .map(String::length)
    .reduce(0, Integer::sum);
// result3 = 13  (0 + 4 + 5 + 4)

int result4 = Stream.<String>empty()
    .map(String::length)
    .reduce(0, Integer::sum);
// result4 = 0  POINT: 空ストリーム → identity 値が返る

// ■ identity の役割
// reduce(0, Integer::sum) の展開:
// Stream.of(4, 5, 4) → ((0 + 4) + 5) + 4 = 13
// ↑ identity 値

// ■ 結合法則の要件（parallel stream での正確性に必要）
// (a + b) + c == a + (b + c)  ← Integer::sum は結合法則を満たす OK:
// (a - b) - c != a - (b - c)  ← 減算は結合法則を満たさない NG:
// → parallel stream で不正確な結果になる可能性

// ■ 3引数 reduce（並列ストリーム向け）
// reduce(identity, accumulator, combiner)
int total = Stream.of("Java", "Scala", "Rust")
    .parallel()
    .reduce(0,
        (sum, str) -> sum + str.length(),  // accumulator
        Integer::sum);  // combiner（並列時のマージ）