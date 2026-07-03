// ■ B: Records + 防御的コピー
public record Order(
    Long id,
    String customerName,  // POINT: String は不変型
    List<OrderItem> items,  // POINT: List は可変型 → 防御的コピー必要
    LocalDateTime createdAt  // POINT: LocalDateTime は不変型
) {
    public Order {  // コンパクトコンストラクタ
        Objects.requireNonNull(customerName);
        items = List.copyOf(items);  // POINT: 防御的コピー（要素 OrderItem も不変であることが前提）
        // List.copyOf() → 独立コピー + null 要素拒否 + 変更不可
    }
    // POINT: items() アクセサが返す List は既に不変なのでそのまま返して安全
}

// ■ C: 従来クラスでの防御的コピー
public final class Config {
    private final List<String> endpoints;
    private final Map<String, String> settings;

    public Config(List<String> endpoints, Map<String, String> settings) {
        // POINT: 入力の防御的コピー（呼び出し元の参照からの変更を遮断）
        this.endpoints = List.copyOf(endpoints);  // 独立コピー + 不変
        this.settings = Map.copyOf(settings);  // 独立コピー + 不変
    }

    public List<String> getEndpoints() {
        return endpoints;  // POINT: 既に不変コピーなので安全にそのまま返却
    }

    public Map<String, String> getSettings() {
        return settings;
    }
}

// ■ 不変性の段階
// レベル1: フィールドが final → 参照の再代入防止（浅い不変性）
// レベル2: フィールドが final + 参照先が不変型 → 深い不変性
// レベル3: レベル2 + 防御的コピー → 外部からの変更影響を遮断しやすい

// NG: 浅い不変性の罠
public final class BadExample {
    private final List<String> items;  // final だが...
    public BadExample(List<String> items) {
        this.items = items;  // POINT: 参照をそのまま保持（防御的コピーなし）
    }
    public List<String> getItems() { return items; }
}
// 呼び出し側: list.add("hack") → BadExample 内の items も変更される！