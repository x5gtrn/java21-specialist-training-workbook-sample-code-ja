// ■ STR プロセッサ（標準: 単純な文字列埋め込み）
String name = "Alice";
double price = 29.99;
String msg = STR."Hello \{name}, your total is $\{price}";
// → "Hello Alice, your total is $29.99"

// ■ 式の中では任意の Java 式が使用可能
String info = STR."""
    Name:    \{user.name()}
    Balance: \{user.balance() > 0 ? "Positive" : "Negative"}
    Items:   \{cart.items().size()}
    """;

// ■ テンプレートプロセッサの仕組み（概念）
// STR."Hello \{name}, total: $\{price}"
// → テンプレートオブジェクト生成
// fragments: ["Hello ", ", total: $", ""]
// values:    [name, price]
// → STR プロセッサが fragments と values を結合

// ■ カスタムプロセッサの可能性（将来の拡張）
// SQL."SELECT * FROM users WHERE name = \{userInput}"
// → SQL プロセッサ:
// PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE name = ?");
// ps.setString(1, userInput);  ← 自動パラメータバインディング
// → SQL インジェクションを構造的に防止

// ■ 式の評価タイミング
int x = 10;
String result = STR."\{x * 2 + 1}";  // "21" — POINT: 実行時に評価
// コンパイル時定数ではない！