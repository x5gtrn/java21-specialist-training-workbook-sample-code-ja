// ■ 従来の Strategy パターン（Java 8 以前）
interface DiscountStrategy {
    BigDecimal calculate(BigDecimal price, int quantity);
}
class RegularDiscount implements DiscountStrategy { /* 実装省略 */ }  // ← ボイラープレート
class VipDiscount implements DiscountStrategy { /* 実装省略 */ }  // ← ボイラープレート
class BulkDiscount implements DiscountStrategy { /* 実装省略 */ }  // ← ボイラープレート
// → 戦略を1つ増やすたびにクラスが1つ増える

// ■ モダン Java 21 スタイル: 同じ DiscountStrategy をラムダで実装
// 単一抽象メソッドなので個別クラスは不要。意図を明示するなら宣言側に
// @FunctionalInterface を付ける（アノテーションがなくてもラムダ自体は可能）。

class PricingService {
    // POINT: 戦略をラムダで定義（クラス不要）
    static final DiscountStrategy REGULAR =
        (price, qty) -> price;
    static final DiscountStrategy VIP =
        (price, qty) -> price.multiply(BigDecimal.valueOf(0.80));
    static final DiscountStrategy BULK =
        (price, qty) -> qty > 100
            ? price.multiply(BigDecimal.valueOf(0.70))
            : price;

    // POINT: Map でルックアップ（Open-Closed 原則に従う拡張方法）
    private static final Map<CustomerType, DiscountStrategy> STRATEGIES = Map.of(
        CustomerType.REGULAR, REGULAR,
        CustomerType.VIP, VIP,
        CustomerType.BULK, BULK
    );

    public BigDecimal calculatePrice(BigDecimal unitPrice, int qty, CustomerType type) {
        DiscountStrategy strategy = STRATEGIES.getOrDefault(type, REGULAR);
        return strategy.calculate(unitPrice, qty).multiply(BigDecimal.valueOf(qty));
    }
}

// ■ テスト: ラムダ戦略も独立テスト可能
@Test
void vipDiscountShouldApply20Percent() {
    BigDecimal result = PricingService.VIP.calculate(BigDecimal.valueOf(100), 1);
    assertThat(result).isEqualByComparingTo("80.00");
}

// ■ メソッド参照による戦略
class DiscountCalculations {
    static BigDecimal seasonal(BigDecimal price, int qty) {
        return price.multiply(BigDecimal.valueOf(0.85));
    }
}
DiscountStrategy seasonal = DiscountCalculations::seasonal;