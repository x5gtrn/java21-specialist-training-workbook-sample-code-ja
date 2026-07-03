// service モジュール
module service {
    requires model;
    exports com.service;
}
@@BLOCK@@
// ■ 修正前: app から model にアクセスできない
// model モジュール
module model {
    exports com.model;
}

// service モジュール
module service {
    requires model;  // POINT: 通常の requires
    exports com.service;
}

// app モジュール
module app {
    requires service;
    // app → service → model だが、model に直接アクセスできない
    // → com.model.User を使うとコンパイルエラー
}

// ■ 修正後: requires transitive で推移的に公開
// service モジュール
module service {
    requires transitive model;  // POINT: 推移的依存 → app も model にアクセス可能
    exports com.service;
}

// app モジュール
module app {
    requires service;  // service + model（推移的）が利用可能
    // → com.model.User にアクセス可能！
}