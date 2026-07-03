Map<String, List<String>> requestsByTenant = new HashMap<>();
String tenantId = request.tenantId();
String requestId = request.id();
@@BLOCK@@
requestsByTenant
        .computeIfAbsent(tenantId, key -> new ArrayList<>())
        .add(requestId);
@@BLOCK@@
List<String> ids = requestsByTenant.get(tenantId);
if (ids == null) {
    ids = new ArrayList<>();
    requestsByTenant.put(tenantId, ids);
}
ids.add(requestId);
@@BLOCK@@
V value = map.get(key);
if (value == null) {
    V newValue = mappingFunction.apply(key);
    if (newValue != null) {
        map.put(key, newValue);
        value = newValue;
    }
}
return value;
@@BLOCK@@
// 良い例: 値の生成だけ
map.computeIfAbsent(key, k -> new ArrayList<>());

// 避けたい例: 同じ map を関数内で変更する
map.computeIfAbsent(key, k -> {
    map.put("other", new ArrayList<>());
    return new ArrayList<>();
});
@@BLOCK@@
Map<String, List<String>> grouped = new HashMap<>();

for (Request request : requests) {
    grouped.computeIfAbsent(request.tenantId(), key -> new ArrayList<>())
            .add(request.id());
}
@@BLOCK@@
Map<String, List<String>> immutable = grouped.entrySet().stream()
        .collect(Collectors.toUnmodifiableMap(
                Map.Entry::getKey,
                entry -> List.copyOf(entry.getValue())));
@@BLOCK@@
// 避けたい: Map 更新の中に外部 I/O を混ぜる
cache.computeIfAbsent(key, k -> externalClient.fetch(k));

// より読みやすい: 取得と登録の責務を明示する
Value loaded = externalClient.fetch(key);
cache.putIfAbsent(key, loaded);
@@BLOCK@@
// 件数を数えるなら merge が読みやすいこともある
Map<String, Integer> counts = new HashMap<>();
counts.merge(tenantId, 1, Integer::sum);