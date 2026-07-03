@Service
public class OrderService {
    private final Counter orderCounter;
    private final Timer processingTimer;
    private final AtomicInteger activeOrders;

    public OrderService(MeterRegistry registry) {
        // POINT: Counter: 累積カウント（リクエスト数、エラー数等）
        this.orderCounter = Counter.builder("orders.placed.total")
            .tag("region", "asia-pacific")
            .description("Total number of orders placed")
            .register(registry);

        // POINT: Timer: 処理時間分布（レイテンシ計測）
        this.processingTimer = Timer.builder("orders.processing.duration")
            .description("Order processing duration")
            .register(registry);

        // POINT: Gauge: 瞬時値（現在の状態）
        this.activeOrders = registry.gauge("orders.active.count",
            new AtomicInteger(0));
    }

    public Order processOrder(OrderRequest req) {
        activeOrders.incrementAndGet();
        try {
            return processingTimer.record(() -> {
                Order order = createOrder(req);
                orderCounter.increment();
                return order;
            });
        } finally {
            activeOrders.decrementAndGet();
        }
    }
}
// → Prometheus 形式の出力例:
// orders_placed_total{region="asia-pacific"} 1234.0
// orders_processing_duration_seconds_sum 456.78
// orders_processing_duration_seconds_count 1234
// orders_active_count 5.0