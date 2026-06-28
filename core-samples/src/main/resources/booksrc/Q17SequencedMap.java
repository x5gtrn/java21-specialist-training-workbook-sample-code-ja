SequencedMap<String, Integer> map = new LinkedHashMap<>();
map.put("a", 1);
map.put("b", 2);
map.put("c", 3);

SequencedMap<String, Integer> reversed = map.reversed();
System.out.println(reversed.keySet());  // [c, b, a]
@@BLOCK@@
SequencedMap<String, Integer> snapshot =
        new LinkedHashMap<>(map.reversed());
@@BLOCK@@
// reversed() は sort ではなく「出現順序の逆」
SequencedMap<String, Integer> m = new LinkedHashMap<>();
m.put("a", 1);
m.put("z", 2);
m.reversed();  // キーは [z, a]（挿入順 a, z の逆）。自然順 sort の [a, z] とは異なる
@@BLOCK@@
SequencedMap<K, V> reversedView = map.reversed();
SequencedMap<K, V> snapshot = new LinkedHashMap<>(reversedView);
@@BLOCK@@
SequencedMap<String, Task> queue = new LinkedHashMap<>();
Task oldest = queue.firstEntry().getValue();
Task newest = queue.lastEntry().getValue();