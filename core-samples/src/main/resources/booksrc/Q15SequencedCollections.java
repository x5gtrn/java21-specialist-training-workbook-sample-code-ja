// ■ Java 21 の新しいコレクション階層
//
// Iterable
// └── Collection
//     ├── SequencedCollection ← POINT: 新規
//     │   ├── List
//     │   ├── Deque
//     │   └── SequencedSet ← POINT: 新規
//     │       ├── SortedSet → NavigableSet
//     │       └── LinkedHashSet
//     └── Set
//
// Map
// └── SequencedMap ← POINT: 新規
//     ├── SortedMap → NavigableMap
//     └── LinkedHashMap

// SequencedCollection の統一 API
List<String> list = new ArrayList<>(List.of("A", "B", "C"));
list.getFirst();  // "A"  ← 従来: list.get(0)
list.getLast();  // "C"  ← 従来: list.get(list.size()-1)
list.addFirst("Z");  // [Z, A, B, C]
list.addLast("D");  // [Z, A, B, C, D]
list.removeFirst();  // "Z" を削除して返す
list.removeLast();  // "D" を削除して返す

// reversed() — 逆順ビュー（コピーではない）
List<String> rev = list.reversed();  // [C, B, A]（元リストのビュー）
rev.getFirst();  // "C"（元リストの getLast()と同等）

// SortedSet でも同じ API
SortedSet<Integer> sorted = new TreeSet<>(List.of(3, 1, 4, 1, 5));  // List は重複可。TreeSet が一意化
sorted.getFirst();  // 1  ← 従来: sorted.first()
sorted.getLast();  // 5  ← 従来: sorted.last()
sorted.reversed();  // {5, 4, 3, 1} のビュー

// SequencedMap
LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
map.put("x", 1); map.put("y", 2); map.put("z", 3);
map.firstEntry();  // x=1 ← 従来: 簡単に取得する方法がなかった
map.lastEntry();  // z=3
map.putFirst("w", 0);  // {w=0, x=1, y=2, z=3}
map.sequencedKeySet();  // [w, x, y, z]
map.sequencedValues();  // [0, 1, 2, 3]
map.reversed().firstEntry();  // z=3