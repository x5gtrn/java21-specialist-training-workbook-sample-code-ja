public record PlaceOrderCommand(
    CustomerId customerId,
    List<OrderLineCommand> lines,
    String idempotencyKey
) {}
@@BLOCK@@
@Transactional
public OrderId handle(PlaceOrderCommand command) {
    Order order = Order.place(command.customerId(), command.lines());
    orderRepository.save(order);
    outboxRepository.save(OutboxEvent.orderPlaced(order.id()));
    return order.id();
}
@@BLOCK@@
public record OutboxEvent(
    UUID id,
    String aggregateType,
    String aggregateId,
    String eventType,
    String payload,
    Instant createdAt
) {}
@@BLOCK@@
sealed interface OrderCommand permits PlaceOrderCommand, CancelOrderCommand {}