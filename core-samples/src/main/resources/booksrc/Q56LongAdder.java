Map<String, Integer> counts = new HashMap<>();

void record(String type) {
    Integer current = counts.get(type);
    counts.put(type, current == null ? 1 : current + 1);
}
@@BLOCK@@
ConcurrentHashMap<String, LongAdder> counts = new ConcurrentHashMap<>();

void record(String type) {
    counts.computeIfAbsent(type, key -> new LongAdder())
            .increment();
}

long count(String type) {
    LongAdder adder = counts.get(type);
    return adder == null ? 0L : adder.sum();
}
@@BLOCK@@
// counts["LOGIN"] == 10

// スレッド A
current = counts.get("LOGIN");  // 10

// スレッド B
current = counts.get("LOGIN");  // 10

// スレッド A
counts.put("LOGIN", 11);

// スレッド B
counts.put("LOGIN", 11);  // 本来12になるべきだが11のまま
@@BLOCK@@
ConcurrentHashMap<String, Integer> counts = new ConcurrentHashMap<>();

void stillRisky(String type) {
    Integer current = counts.get(type);
    counts.put(type, current == null ? 1 : current + 1);
}
@@BLOCK@@
LongAdder adder = counts.computeIfAbsent(type, key -> new LongAdder());
adder.increment();
@@BLOCK@@
private final ConcurrentHashMap<String, LongAdder> counts = new ConcurrentHashMap<>();

public void record(String type) {
    counts.computeIfAbsent(type, ignored -> new LongAdder())
            .increment();
}

public Map<String, Long> snapshot() {
    return counts.entrySet().stream()
            .collect(Collectors.toUnmodifiableMap(
                    Map.Entry::getKey,
                    entry -> entry.getValue().sum()));
}
@@BLOCK@@
// メトリクスには向いている
httpRequests.computeIfAbsent(path, ignored -> new LongAdder()).increment();

// 厳密な残高更新には向かない
// balanceAdders.computeIfAbsent(accountId, ignored -> new LongAdder()).add(amount);