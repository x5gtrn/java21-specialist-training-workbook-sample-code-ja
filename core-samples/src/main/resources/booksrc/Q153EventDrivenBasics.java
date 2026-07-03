// ■ イベント発行（Spring + Kafka）
@Service
public class OrderService {
    private final KafkaTemplate<String, OrderEvent> kafka;

    @Transactional
    public Order placeOrder(OrderRequest req) {
        Order order = orderRepository.save(new Order(req));
        // POINT: イベント発行（誰が消費するか知らない）
        kafka.send("order-events",
            new OrderPlacedEvent(order.id(), order.items(), order.total()));
        return order;
    }
}

// ■ イベント消費（独立したサービス）
// 在庫サービス
@KafkaListener(topics = "order-events", groupId = "inventory-service")
public void onOrderPlaced(OrderPlacedEvent event) {
    inventoryService.reserveItems(event.items());
}

// 通知サービス
@KafkaListener(topics = "order-events", groupId = "notification-service")
public void onOrderPlaced(OrderPlacedEvent event) {
    emailService.sendOrderConfirmation(event.orderId());
}

// 分析サービス（後から追加 — OrderService の変更不要）
@KafkaListener(topics = "order-events", groupId = "analytics-service")
public void onOrderPlaced(OrderPlacedEvent event) {
    analyticsService.recordSale(event);
}

// POINT: 時間的分離の例
// 通知サービスがデプロイ中にダウン → Kafka がイベントを保持
// 通知サービスが復旧 → 未処理のイベントを順次処理