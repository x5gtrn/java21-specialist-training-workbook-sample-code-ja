sealed interface Shipment permits Domestic, International {}
record Domestic(double weight) implements Shipment {}
record International(double weight, String country) implements Shipment {}

double calculateFee(Shipment s) {
    return switch (s) {
        case Domestic d when d.weight() > 30 -> d.weight() * 2.0;
        case Domestic d                       -> d.weight() * 1.0;
        case International i when "JP".equals(i.country()) -> i.weight() * 1.5;
        case International i                  -> i.weight() * 3.0;
    };
}
@@BLOCK@@
// when ガードの評価順序
// POINT: 上から順に評価される（順序が重要）

return switch (s) {
    // 1. まず Domestic 型か判定
    case Domestic d when d.weight() > 30 -> d.weight() * 2.0;
    // → Domestic かつ 30kg 超 → このブランチ

    case Domestic d -> d.weight() * 1.0;
    // → Domestic だが 30kg 以下 → このブランチ（フォールバック）

    // 2. 次に International 型か判定
    case International i when "JP".equals(i.country()) -> i.weight() * 1.5;
    // → International かつ 日本向け → このブランチ

    case International i -> i.weight() * 3.0;
    // → International だが日本以外 → このブランチ（フォールバック）
};

// NG: 順序を逆にするとコンパイルエラー
return switch (s) {
    case Domestic d -> d.weight() * 1.0;  // 全 Domestic にマッチ
    case Domestic d when d.weight() > 30 -> d.weight() * 2.0;  // NG: 到達不能コード
    // エラー: this case label is dominated by a preceding case label
    // ...
};

// POINT: sealed 型 + default なしの利点
// 将来 Express という新サブタイプを追加した場合:
// sealed interface Shipment permits Domestic, International, Express {}
// → 上記の switch 式はコンパイルエラーとなり、Express の処理追加を強制される
// → default があると、Express が黙って default に落ちてバグの原因に