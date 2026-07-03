public interface PricingStrategy {
    default double calculateRetailPrice(double cost) {
        validatePositive(cost);  // POINT: private ヘルパーを呼び出し
        return cost * 1.3;
    }

    default double calculateWholesalePrice(double cost) {
        validatePositive(cost);  // POINT: 同じ private ヘルパーを再利用
        return cost * 1.1;
    }

    // POINT: Java 9+ : インターフェースの private メソッド
    private void validatePositive(double value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Value must be positive: " + value);
        }
    }
}