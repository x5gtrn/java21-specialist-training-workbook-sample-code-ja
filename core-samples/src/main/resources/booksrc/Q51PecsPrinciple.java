// D: 正しい PECS 適用
public static <T> void transfer(Collection<? extends T> source,
                                  Collection<? super T> dest) {
    for (T item : source) {  // source から読み取り（Producer → extends）
        dest.add(item);  // dest に書き込み（Consumer → super）
    }
}

// 使用例
List<Integer> integers = List.of(1, 2, 3);
List<Number> numbers = new ArrayList<>();
transfer(integers, numbers);  // OK: Integer extends Number
// T は Number に推論される
// source: Collection<? extends Number> ← List<Integer> は OK
// dest:   Collection<? super Number>   ← List<Number> は OK

List<Double> doubles = List.of(1.1, 2.2);
List<Object> objects = new ArrayList<>();
transfer(doubles, objects);  // OK: Double extends Object

// A の問題点
public static <T> void transferBad(Collection<T> source, Collection<T> dest) { /* 省略 */ }
// transferBad(integers, numbers); NG: コンパイルエラー
// Integer ≠ Number（不変のジェネリクス）

// java.util.Collections の実際の API
// Collections.copy(List<? super T> dest, List<? extends T> src)
// POINT: まさに PECS パターン

// ■ PECS の覚え方
// Producer (データを提供する側) → ? Extends T  → 読み取り専用
// Consumer (データを受け取る側) → ? Super T    → 書き込み専用
// PE-CS → "Producer Extends, Consumer Super"