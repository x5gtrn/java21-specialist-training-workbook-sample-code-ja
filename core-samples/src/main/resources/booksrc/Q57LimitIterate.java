// ■ 無限ストリーム + limit() の安全な利用
List<SensorReading> readings = Stream.generate(sensor::read)  // 無限ストリーム
    .filter(SensorReading::isValid)  // 有効なもののみ（中間操作）
    .limit(1000)  // POINT: 最初の1000件で全体を停止
    .collect(Collectors.toList());  // ターミナル操作
// → generate()は1000件の有効データが集まった時点で停止
// → 無限に読み続けることはない

// ■ iterate() + limit()
List<Long> fibonacci = Stream.iterate(
        new long[]{0, 1},
        f -> new long[]{f[1], f[0] + f[1]})
    .limit(20)  // POINT: 最初の20項
    .map(f -> f[0])
    .collect(Collectors.toList());
// [0, 1, 1, 2, 3, 5, 8, 13, 21, 34, ...]

// ■ 短絡的操作の一覧
// 中間操作（short-circuiting）: limit(), takeWhile() (Java 9+)
// ターミナル操作（short-circuiting）: findFirst(), findAny(), anyMatch(), allMatch(), noneMatch()

// ■ limit() vs takeWhile() (Java 9+)
Stream.of(1, 2, 3, 4, 5, 6).limit(3);  // [1, 2, 3] — 個数ベース
Stream.of(1, 2, 3, 4, 5, 6).takeWhile(n -> n < 4);  // [1, 2, 3] — 条件ベース