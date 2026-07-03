Map<CustomerKey, Profile> profiles = new HashMap<>();
CustomerKey key = new CustomerKey("tenant-a", "user-1");
profiles.put(key, profile);

key.setUserId("user-2");  // userId は equals/hashCode で使われる
Profile found = profiles.get(key);
@@BLOCK@@
record CustomerKey(String tenantId, String userId) {
    CustomerKey {
        if (tenantId == null || tenantId.isBlank()) {
            throw new IllegalArgumentException("tenantId is required");
        }
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId is required");
        }
    }
}
@@BLOCK@@
CustomerKey key = new CustomerKey("tenant-a", "user-1");
profiles.put(key, profile);  // hash("tenant-a", "user-1") のバケットへ格納

key.setUserId("user-2");  // hash("tenant-a", "user-2") に変化
profiles.get(key);  // 新しいハッシュに基づくバケットを探す
@@BLOCK@@
for (Map.Entry<CustomerKey, Profile> entry : profiles.entrySet()) {
    System.out.println(entry.getKey() + " -> " + entry.getValue());
}
@@BLOCK@@
record CustomerKey(String tenantId, String userId) {}

Map<CustomerKey, Profile> profiles = new HashMap<>();
profiles.put(new CustomerKey("tenant-a", "user-1"), profile);

Profile found = profiles.get(new CustomerKey("tenant-a", "user-1"));
@@BLOCK@@
record SearchKey(String tenantId, List<String> tags) {
    SearchKey {
        tags = List.copyOf(tags);
    }
}
@@BLOCK@@
List<String> tags = new ArrayList<>(List.of("a"));
SearchKey key = new SearchKey("tenant-a", tags);
map.put(key, value);

tags.add("b");  // SearchKey 内部には影響しない