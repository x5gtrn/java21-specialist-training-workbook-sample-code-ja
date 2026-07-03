// POINT: Domain 層（フレームワーク依存ゼロ）
public interface OrderRepository {  // 出力ポート
    Order save(Order order);
    Optional<Order> findById(OrderId id);
}

public class OrderService implements CreateOrderUseCase {  // Input Port 実装
    private final OrderRepository repository;  // 抽象に依存（DIP）
    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }
    public Order create(CreateOrderCommand cmd) {
        Order order = Order.create(cmd.productId(), cmd.quantity());
        return repository.save(order);
    }
}

// POINT: Adapter 層（インフラ実装）
@Repository
public class JpaOrderRepository implements OrderRepository {
    // JPA の詳細はここに閉じ込められる
}