List<String> products = List.of("Laptop", "Phone", "Tablet");

// Stream.toList() → 変更不可能なリスト
List<String> unmodifiable = products.stream()
    .filter(p -> p.length() > 4)
    .toList();  // [Laptop, Phone, Tablet]
// unmodifiable.add("Watch"); // ← UnsupportedOperationException

// Collectors.toList() → 仕様上は型・変更可能性を保証しない
List<String> modifiable = products.stream()
    .filter(p -> p.length() > 4)
    .collect(Collectors.toList());  // [Laptop, Phone, Tablet]
modifiable.add("Watch");  // 現在の JDK 実装では多くの場合 OK だが、仕様上の保証ではない

// null 要素の取り扱い
List<String> withNull = Stream.of("A", null, "B").toList();  // OK: [A, null, B]（null 許容）
List<String> withNull2 = Stream.of("A", null, "B")
    .collect(Collectors.toList());  // OK: [A, null, B]（null 許容）
// 注意: List.of("A", null) は NullPointerException を投げる