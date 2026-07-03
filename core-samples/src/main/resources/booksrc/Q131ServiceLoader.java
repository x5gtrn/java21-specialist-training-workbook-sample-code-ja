// サービスインターフェース（API モジュール）
// com.plugin.api モジュール
module com.plugin.api {
    exports com.plugin.api;  // インターフェースを公開
}

// サービスインターフェース
package com.plugin.api;
public interface Plugin {
    String getName();
    void execute();
}

// サービスプロバイダ（実装モジュール）
// com.plugin.provider モジュール
module com.plugin.provider {
    requires com.plugin.api;
    provides com.plugin.api.Plugin  // POINT: サービスインターフェース
        with com.plugin.impl.DefaultPlugin;  // POINT: 実装クラス
    // 注意: com.plugin.impl パッケージの exports は不要！
}

// サービスコンシューマ（使用側モジュール）
// com.plugin.consumer モジュール
module com.plugin.consumer {
    requires com.plugin.api;
    uses com.plugin.api.Plugin;  // POINT: uses はサービスの使用を宣言
}

// 使用側コード
ServiceLoader<Plugin> loader = ServiceLoader.load(Plugin.class);
for (Plugin plugin : loader) {
    plugin.execute();  // 発見された全実装に対して実行
}