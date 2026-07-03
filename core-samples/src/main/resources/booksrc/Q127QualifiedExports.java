// ■ engine モジュールの module-info.java
module com.schedule.engine {
    // POINT: D: 修飾エクスポート（qualified exports）
    exports com.schedule.engine.api to com.schedule.web;
    // → com.schedule.web のみが com.schedule.engine.api にアクセス可能
    // → com.other.module からはアクセス不可能

    // 内部実装パッケージ → exports なし → 自動的にカプセル化
    // com.schedule.engine.internal → モジュール外からアクセス不可
}

// ■ web モジュールの module-info.java
module com.schedule.web {
    requires com.schedule.engine;  // engine モジュールに依存
    // → com.schedule.engine.api パッケージにアクセス可能（修飾 exports で許可済み）
}

// ■ 非修飾 vs 修飾 exports
// exports com.schedule.engine.api;                     → 全モジュールに公開
// exports com.schedule.engine.api to com.schedule.web; → web のみに公開

// ■ 複数モジュールに修飾エクスポート
// exports com.schedule.engine.api to com.schedule.web, com.schedule.test;
// → web と test の2モジュールのみに公開

// ■ JDK 自体での使用例
// java.base モジュールの module-info.java:
// exports sun.nio.ch to java.management, jdk.sctp;
// → 内部パッケージを限定的に他の JDK モジュールに公開