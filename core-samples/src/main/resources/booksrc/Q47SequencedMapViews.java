// SequencedMap の操作一覧
LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
map.put("B", 2);
map.put("C", 3);
map.put("D", 4);
// 現在: {B=2, C=3, D=4}

// putFirst / putLast
map.putFirst("A", 1);  // POINT: 先頭に挿入 → {A=1, B=2, C=3, D=4}
map.putLast("E", 5);  // POINT: 末尾に挿入 → {A=1, B=2, C=3, D=4, E=5}

// 既存キーの移動
map.putFirst("D", 40);  // POINT: D を先頭に移動＋値更新 → {D=40, A=1, B=2, C=3, E=5}

// firstEntry / lastEntry（読み取りのみ、削除しない）
map.firstEntry();  // D=40
map.lastEntry();  // E=5

// pollFirstEntry / pollLastEntry（取得して削除）
Map.Entry<String, Integer> first = map.pollFirstEntry();  // D=40 を取得して削除
Map.Entry<String, Integer> last = map.pollLastEntry();  // E=5 を取得して削除
// 残り: {A=1, B=2, C=3}

// sequencedKeySet / sequencedValues / sequencedEntrySet
SequencedSet<String> keys = map.sequencedKeySet();  // [A, B, C]
SequencedCollection<Integer> vals = map.sequencedValues();  // [1, 2, 3]

// reversed() — 逆順ビュー
SequencedMap<String, Integer> rev = map.reversed();
rev.firstEntry();  // C=3（元マップの最後）
rev.forEach((k, v) -> System.out.println(k + "=" + v));
// C=3, B=2, A=1

// SortedMap (TreeMap) でも SequencedMap を実装
TreeMap<String, Integer> tree = new TreeMap<>(map);
tree.putFirst("Z", 99);  // NG: UnsupportedOperationException
// TreeMap はソート順がキーの自然順序で決まるため、putFirst/putLast は不可
// ただし firstEntry/lastEntry/pollFirstEntry 等は使用可能