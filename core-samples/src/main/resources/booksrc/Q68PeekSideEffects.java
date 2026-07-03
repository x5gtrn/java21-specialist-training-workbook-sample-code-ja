List<Order> valid = orders.stream()
        .peek(order -> log.debug("received {}", order.id()))
        .filter(Order::isValid)
        .peek(order -> log.debug("valid {}", order.id()))
        .toList();
@@BLOCK@@
orders.stream()
        .filter(Order::isValid)
        .peek(order -> orderRepository.save(order))
        .peek(order -> auditClient.send(order))
        .toList();
@@BLOCK@@
Stream<Order> stream = orders.stream()
        .peek(order -> auditClient.send(order));

// ここまででは何も実行されない
@@BLOCK@@
long count = stream.count();
@@BLOCK@@
Optional<Order> first = orders.stream()
        .peek(order -> auditClient.send(order))
        .filter(Order::isValid)
        .findFirst();
@@BLOCK@@
List<Order> validOrders = orders.stream()
        .filter(Order::isValid)
        .toList();

for (Order order : validOrders) {
    orderRepository.save(order);
    auditClient.send(order);
}
@@BLOCK@@
List<Order> validOrders = orders.stream()
        .filter(Order::isValid)
        .toList();

validOrders.forEach(orderService::persistAndAudit);
@@BLOCK@@
List<Order> validOrders = orders.stream()
        .filter(Order::isValid)
        .toList();

transactionTemplate.executeWithoutResult(status -> {
    for (Order order : validOrders) {
        orderRepository.save(order);
    }
});

validOrders.forEach(auditClient::sendOrderAccepted);