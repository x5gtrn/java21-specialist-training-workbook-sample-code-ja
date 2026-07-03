// ■ Entity — 一意の ID で識別、状態が変化しても同一
@Entity  // JPA の @Entity。DDD の Entity 概念とは別物（永続化の例として併記）
public class Order {  // Entity: OrderId で識別
    @Id private OrderId id;
    private OrderStatus status;
    private List<OrderLine> lines;  // Value Object 群
    private Money totalAmount;  // 値オブジェクト
}

// ■ Value Object — 属性値で識別、不変
public record Money(BigDecimal amount, Currency currency) {}
// Money(100, JPY) == Money(100, JPY) → 同一と見なす（ID なし）
// Record は Value Object に最適

public record Address(String street, String city, String zip) {}

// ■ Aggregate — 整合性の境界
// Order Aggregate = Order(root) + OrderLine(子) + Money(VO)
// 外部からは Order (Aggregate Root) 経由でのみアクセス
// OrderLine を直接変更するのは禁止