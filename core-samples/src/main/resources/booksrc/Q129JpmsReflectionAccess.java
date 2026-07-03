// ■ module-info.java の各ディレクティブの比較

module app.core {
    // exports → コンパイル時 + 実行時の public アクセス
    exports com.app.api;  // public クラス/メソッドのみ、private は不可

    // opens → ランタイムリフレクション（private 含む）
    opens com.app.model to framework.di;  // POINT: DI 用: private フィールド注入に必要
    opens com.app.entity to hibernate.core;  // POINT: ORM 用: private フィールドマッピングに必要

    // POINT: opens (非修飾) → 全モジュールにリフレクションアクセスを許可
    // opens com.app.model;  ← セキュリティ上推奨されない

    // POINT: open module → モジュール全体を opens（テストや移行期に使用）
    // open module app.core { ... }  ← すべてのパッケージが opens 扱い
}

// ■ exports vs opens の違い
//
// ディレクティブ  | コンパイル時  | 実行時(public) | 実行時(private, リフレクション)
// --------------|------------|-------------|---------------------------
// (なし)         | NG: | NG: | NG:
// exports       | OK: public  | OK: public   | NG:
// opens         | NG: | NG: | OK: (setAccessible 可能)
// exports+opens | OK: public  | OK: public   | OK:

// ■ エラー例
// Field field = User.class.getDeclaredField("email");
// field.setAccessible(true);  // opens なし → InaccessibleObjectException
// field.set(user, "new@email.com");