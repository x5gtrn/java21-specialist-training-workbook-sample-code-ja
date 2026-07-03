sealed interface PaymentMethod
        permits CreditCard, BankTransfer, CryptoWallet {}

record CreditCard(String brand, boolean international) implements PaymentMethod {}
record BankTransfer(String bankCode) implements PaymentMethod {}
record CryptoWallet(String network) implements PaymentMethod {}

BigDecimal feeFor(PaymentMethod method) {
    return switch (method) {
        case CreditCard c    -> c.international()
                ? new BigDecimal("0.035")
                : new BigDecimal("0.025");
        case BankTransfer b  -> new BigDecimal("0.010");
        case CryptoWallet cw -> new BigDecimal("0.050");
    };
}
@@BLOCK@@
sealed interface PaymentMethod
        permits CreditCard, BankTransfer, CryptoWallet, BuyNowPayLater {}

record BuyNowPayLater(String provider) implements PaymentMethod {}
@@BLOCK@@
BigDecimal feeFor(PaymentMethod method) {
    return switch (method) {
        case CreditCard c    -> c.international()
                ? new BigDecimal("0.035")
                : new BigDecimal("0.025");
        case BankTransfer b  -> new BigDecimal("0.010");
        case CryptoWallet cw -> new BigDecimal("0.050");
        default              -> new BigDecimal("0.030");
    };
}
@@BLOCK@@
// 1. sealed interface によって許可サブタイプが限定される
sealed interface PaymentMethod
        permits CreditCard, BankTransfer, CryptoWallet {}

// 2. 各サブタイプは明示的に定義される
record CreditCard(String brand, boolean international) implements PaymentMethod {}
record BankTransfer(String bankCode) implements PaymentMethod {}
record CryptoWallet(String network) implements PaymentMethod {}

// 3. switch 式はすべての許可サブタイプを明示的に処理する
BigDecimal feeFor(PaymentMethod method) {
    return switch (method) {
        case CreditCard c    -> calculateCardFee(c);
        case BankTransfer b  -> calculateBankFee(b);
        case CryptoWallet cw -> calculateCryptoFee(cw);
    };
}
@@BLOCK@@
BigDecimal feeFor(PaymentMethod method) {
    if (method instanceof CreditCard c) {
        return calculateCardFee(c);
    } else if (method instanceof BankTransfer b) {
        return calculateBankFee(b);
    } else if (method instanceof CryptoWallet cw) {
        return calculateCryptoFee(cw);
    }
    throw new IllegalArgumentException("Unsupported payment method: " + method);
}