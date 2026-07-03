sealed interface Claim permits AutoClaim, HealthClaim {}
record AutoClaim(String vin, double amount) implements Claim {}
non-sealed class HealthClaim implements Claim {
    private String diagnosis;
    // POINT: non-sealed → ここから先は自由に拡張可能
}

// non-sealed から先は制約なし
class SupplementalClaim extends HealthClaim {
    private String supplementType;  // OK: コンパイル成功
}
class DentalClaim extends HealthClaim { }  // OK: これも可能
class VisionClaim extends HealthClaim { }  // OK: いくらでも追加可能

// ■ switch 式への影響
double calculatePayout(Claim claim) {
    return switch (claim) {
        case AutoClaim a   -> a.amount() * 0.9;
        case HealthClaim h -> calculateHealthPayout(h);
        // POINT: SupplementalClaim は HealthClaim のサブクラスなので
        // HealthClaim のケースにマッチする
        // → SupplementalClaim 固有の計算が必要な場合はここでは不十分
    };
}

// ■ SupplementalClaim を個別に処理したい場合
double calculatePayoutDetailed(Claim claim) {
    return switch (claim) {
        case AutoClaim a -> a.amount() * 0.9;
        // POINT: 具体的なサブクラスを先に配置（when ガードは不要）
        case SupplementalClaim s -> calculateSupplemental(s);
        case HealthClaim h       -> calculateHealthPayout(h);
        // POINT: SupplementalClaim は HealthClaim より前に配置する必要がある
        // （逆にすると HealthClaim が先にマッチして到達不能コードになる）
    };
}

// ■ sealed の 3つの修飾子（許可サブタイプが選択必須）
// final      → さらなるサブクラス化を完全に禁止
// sealed     → 限定されたサブタイプのみ許可（再帰的な制約）
// non-sealed → 自由なサブクラス化を許可（「開放ポイント」）