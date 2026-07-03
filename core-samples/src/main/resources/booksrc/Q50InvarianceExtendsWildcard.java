// このメソッドは値を読み取るだけである。リストへ新しい要素は追加しない。
double total(/* parameter */ values) {
    return values.stream()
            .mapToDouble(Number::doubleValue)
            .sum();
}
@@BLOCK@@
double total(List<? extends Number> values) {
    return values.stream()
            .mapToDouble(Number::doubleValue)
            .sum();
}

List<Integer> ints = List.of(1, 2, 3);
List<Double> doubles = List.of(1.5, 2.5);

double a = total(ints);
double b = total(doubles);
@@BLOCK@@
List<Integer> integers = new ArrayList<>();
// 仮にこれが許可されたとする
List<Number> numbers = integers;
numbers.add(3.14);  // Double を追加できてしまう
Integer value = integers.get(0);  // 実体は Double なので型安全性が壊れる
@@BLOCK@@
void printAll(List<? extends Number> values) {
    for (Number n : values) {
        System.out.println(n.doubleValue());
    }
}
@@BLOCK@@
void broken(List<? extends Number> values) {
    // values.add(Integer.valueOf(1)); // コンパイルエラー
    // values.add(Double.valueOf(1.0)); // これもコンパイルエラー
    values.add(null);  // null だけは追加可能だが、実務上有用ではない
}
@@BLOCK@@
double total(List<? extends Number> values) {
    return values.stream()
            .mapToDouble(Number::doubleValue)
            .sum();
}
@@BLOCK@@
// 良い: 受け取る側の柔軟性を上げる
double average(Collection<? extends Number> values) {
    return values.stream()
            .mapToDouble(Number::doubleValue)
            .average()
            .orElse(0.0);
}

// 避けたい: 実装型と具象型に縛りすぎる
double average(ArrayList<Number> values) {
    return values.stream()
            .mapToDouble(Number::doubleValue)
            .average()
            .orElse(0.0);
}