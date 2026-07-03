double[] readings = sensorData.readAll();  // 1000万要素

// POINT: プリミティブストリーム（推奨）: autoboxing なし
DoubleSummaryStatistics stats = DoubleStream.of(readings)
    .filter(v -> v > 0.0 && v < 1000.0)  // 有効範囲フィルタ
    .summaryStatistics();  // POINT: 1パスで全統計量計算

System.out.println("Count: " + stats.getCount());  // 件数
System.out.println("Sum:   " + stats.getSum());  // 合計
System.out.println("Min:   " + stats.getMin());  // 最小
System.out.println("Max:   " + stats.getMax());  // 最大
System.out.println("Avg:   " + stats.getAverage());  // 平均
// POINT: 1000万要素でも GC 負荷は最小限

// NG: ボックス化ストリーム: 1000万回の autoboxing
OptionalDouble avg = Arrays.stream(readings)  // DoubleStream ← POINT: これは OK
    .boxed()  // Stream<Double> ← POINT: ここで autoboxing
    .filter(v -> v > 0.0)  // Double::doubleValue で unboxing
    .mapToDouble(Double::doubleValue)  // DoubleStream に戻す
    .average();
// → boxed() で 1000万個の Double オブジェクト生成 → GC 大量発生

// ■ オブジェクトストリーム → プリミティブストリームへの変換
List<Order> orders = orderRepository.findAll();
int totalQuantity = orders.stream()
    .mapToInt(Order::quantity)  // POINT: Stream<Order> → IntStream
    .sum();  // POINT: IntStream 固有の terminal operation

double totalRevenue = orders.stream()
    .mapToDouble(Order::amount)  // POINT: Stream<Order> → DoubleStream
    .sum();

// ■ プリミティブストリーム固有の操作（Stream<T>にはない）
// sum(), average(), summaryStatistics()
// IntStream: range(start, end), rangeClosed(start, end)
// asLongStream(), asDoubleStream() — 型拡張