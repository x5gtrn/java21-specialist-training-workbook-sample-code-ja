Order order = Order.empty();
for (OrderEvent event : events) {
    order = order.apply(event);  // イベントを順に適用して現在状態を再構築
}
@@BLOCK@@
sealed interface OrderEvent permits OrderCreated, PaymentCaptured {}

record OrderCreated(OrderId orderId, CustomerId customerId) implements OrderEvent {}
record PaymentCaptured(OrderId orderId, Money amount) implements OrderEvent {}
@@BLOCK@@
Order apply(OrderEvent event) {
    return switch (event) {
        case OrderCreated e -> on(e);
        case PaymentCaptured e -> on(e);
    };
}