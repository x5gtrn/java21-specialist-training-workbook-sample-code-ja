// ■ 3つの方法の比較

List<String> original = new ArrayList<>(Arrays.asList("A", "B", "C"));

// 1. Collections.unmodifiableList() — ビュー（ラッパー）
List<String> unmod = Collections.unmodifiableList(original);
original.add("D");
System.out.println(unmod);  // [A, B, C, D] POINT: 元の変更が反映される！
// → unmod は original のビューであり、独立したコピーではない

// 2. List.copyOf() — 独立したコピー
original = new ArrayList<>(Arrays.asList("A", "B", "C"));
List<String> copied = List.copyOf(original);
original.add("D");
System.out.println(copied);  // [A, B, C] POINT: 元の変更は反映されない

// 3. List.of() — ファクトリメソッド（引数から直接作成）
List<String> factory = List.of("A", "B", "C");
// factory.add("D"); → UnsupportedOperationException

// ■ null の扱いの違い
List<String> withNull = Arrays.asList("A", null, "C");

Collections.unmodifiableList(withNull);  // OK — null 許容
List.copyOf(withNull);  // NG: NullPointerException
List.of("A", null, "C");  // NG: NullPointerException

// ■ 実務での使い分け
public class UserService {
    private final List<String> roles;

    // コンストラクタでの防御的コピー（推奨パターン）
    public UserService(List<String> roles) {
        this.roles = List.copyOf(roles);  // POINT: 独立コピー + null 拒否
    }

    // ゲッターでの返却（List.copyOf の結果は既に不変なのでそのまま返せる）
    public List<String> getRoles() {
        return roles;  // 安全: 既に不変コピー
    }
}