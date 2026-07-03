// POINT: Command Side（書き込み最適化）
@Service
public class OrderCommandService {
    public OrderId createOrder(CreateOrderCommand cmd) {
        Order order = Order.create(cmd);
        orderRepository.save(order);  // 正規化された Entity
        eventPublisher.publish(new OrderCreatedEvent(order));
        return order.getId();
    }
}

// POINT: Query Side（読み取り最適化）
@Service
public class OrderQueryService {
    public OrderSummaryDto getOrderSummary(Long orderId) {
        // 非正規化されたビュー/投影
        return orderReadRepository.findSummaryById(orderId);
    }

    public Page<OrderListDto> searchOrders(OrderSearchCriteria criteria, Pageable page) {
        return orderReadRepository.search(criteria, page);
    }
}
// POINT: 同一 DB でもテーブル/ビュー分離で実現可能（別 DB 必須ではない）