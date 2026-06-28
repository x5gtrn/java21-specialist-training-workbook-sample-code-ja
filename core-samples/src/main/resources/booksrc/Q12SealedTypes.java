sealed interface Payment permits CreditCard, BankTransfer, Crypto {}
record CreditCard(String number, YearMonth expiry) implements Payment {}
record BankTransfer(String iban, String bic) implements Payment {}
final class Crypto implements Payment {
    private final String walletAddress;
    // ...
}

// NG: コンパイルエラー（permits リストにない）
// class PayPal implements Payment {}
// エラー: class is not allowed to extend sealed class: Payment

// ■ sealed の制約ルール
// 1. permits で列挙された型のみが実装/継承可能
// 2. 許可サブタイプは同一パッケージ（無名モジュール）or 同一モジュール内に存在必須
// 3. 許可サブタイプは以下のいずれかを宣言必須:
// - final     → さらなるサブクラス化を完全禁止
// - sealed    → 限定されたサブタイプのみ許可（再帰的な制約）
// - non-sealed → 自由なサブクラス化を許可

// ■ switch 式との組み合わせ（網羅性保証）
double processFee(Payment payment) {
    return switch (payment) {
        case CreditCard cc   -> 0.03;
        case BankTransfer bt -> 0.01;
        case Crypto c        -> 0.005;
        // POINT: default 不要（sealed + permits で網羅性が保証）
        // POINT: 新しい型を permits に追加 → ここがコンパイルエラー → 修正を強制
    };
}