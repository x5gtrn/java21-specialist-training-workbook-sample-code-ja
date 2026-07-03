module com.lib {
    exports com.lib.api;  // POINT: B: API パッケージを公開

    // com.lib.internal は何も宣言なし
    // → POINT: D: 自動的にカプセル化
    // → モジュール外からアクセス不可（コンパイル時も実行時も）
}

// ■ 他のモジュールからのアクセス
import com.lib.api.UserService;  // OK: exports されている → OK
import com.lib.internal.UserDaoImpl;  // NG: コンパイルエラー: パッケージ非公開

// ■ リフレクションでも不可（opens されていないため）
Class<?> c = Class.forName("com.lib.internal.UserDaoImpl");  // クラス解決自体は可能な場合がある
// 取得後に setAccessible(true) で非公開メンバーへアクセスしようとすると
// → java.lang.reflect.InaccessibleObjectException がスローされる

// ■ JPMS の 4つの主要ディレクティブ
// exports pkg;            → コンパイル時+実行時の public アクセスを許可
// exports pkg to mod;     → 特定モジュールのみに限定（修飾 exports）
// opens pkg;              → ランタイムリフレクション（private 含む）を許可
// opens pkg to mod;       → 特定モジュールのみにリフレクション許可

// ■ exports vs opens の根本的な違い
// exports → 「このパッケージを使ってコードを書いてよい」
// opens   → 「このパッケージにリフレクションでアクセスしてよい」
// → DI フレームワーク（Spring）や ORM（Hibernate）は opens が必要
// → 通常の利用は exports で十分