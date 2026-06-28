List<String> items = List.of("A", "B", "C");
items.addFirst("Z");
@@BLOCK@@
// ミュータブルリスト → 変更操作 OK
List<String> mutable = new ArrayList<>(List.of("A", "B", "C"));
mutable.addFirst("Z");  // OK: ["Z", "A", "B", "C"]
mutable.addLast("D");  // OK: ["Z", "A", "B", "C", "D"]
mutable.removeFirst();  // OK: "Z" を削除して返す
mutable.removeLast();  // OK: "D" を削除して返す

// 不変リスト → 読み取り操作のみ OK
List<String> immutable = List.of("A", "B", "C");
immutable.getFirst();  // OK: "A"（読み取り OK）
immutable.getLast();  // OK: "C"（読み取り OK）
immutable.reversed();  // OK: [C, B, A]（ビュー、変更なし）
immutable.addFirst("Z");  // NG: UnsupportedOperationException
immutable.addLast("D");  // NG: UnsupportedOperationException
immutable.removeFirst();  // NG: UnsupportedOperationException

// Collections.unmodifiableList() も同様
List<String> unmod = Collections.unmodifiableList(new ArrayList<>(List.of("X", "Y")));
unmod.getFirst();  // OK: "X"
unmod.addFirst("W");  // NG: UnsupportedOperationException

// POINT: SequencedCollection メソッドの分類
// 読み取り: getFirst(), getLast(), reversed()   → 不変リストでも OK
// 変更:     addFirst(), addLast(), removeFirst(), removeLast() → 不変リストで NG