// 典型的な N+1
List<Order> orders = orderRepository.findRecentOrders();
for (Order order : orders) {
    // 各 order で items を lazy load
    total += order.getItems().size();
}