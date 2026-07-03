public class OrderProcessor {
    var orderCount = 0;  // 行1

    public void process() {
        var items = List.of("A", "B", "C");  // 行2
        var filtered = items.stream()
                           .filter(s -> s.startsWith("A"))
                           .collect(Collectors.toList());  // 行3
        for (var item : filtered) {  // 行4
            System.out.println(item);
        }
    }
}
@@BLOCK@@
// 行1: NG: コンパイルエラー
// var はインスタンスフィールドには使用不可
var orderCount = 0;  // → int orderCount = 0; に修正が必要

// 行2: OK: 有効 → List<String> に推論
var items = List.of("A", "B", "C");

// 行3: OK: 有効 → List<String> に推論（ストリームは型情報を保持する）
var filtered = items.stream()
                    .filter(s -> s.startsWith("A"))
                    .collect(Collectors.toList());

// 行4: OK: 有効 → String に推論（拡張 for 文で var 使用可能）
for (var item : filtered) {
    System.out.println(item);
}