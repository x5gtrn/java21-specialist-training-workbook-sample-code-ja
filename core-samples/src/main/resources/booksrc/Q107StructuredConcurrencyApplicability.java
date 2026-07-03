try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    var user = scope.fork(() -> userClient.fetch(userId));
    var orders = scope.fork(() -> orderClient.fetch(userId));
    var risk = scope.fork(() -> riskClient.fetch(userId));

    scope.join();
    scope.throwIfFailed();

    return new Response(user.get(), orders.get(), risk.get());
}
@@BLOCK@@
executor.submit(() -> userClient.fetch(userId));
executor.submit(() -> orderClient.fetch(userId));
// `join()` しない、失敗も見ない
@@BLOCK@@
try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    var a = scope.fork(this::callA);
    var b = scope.fork(this::callB);
    scope.join();
    scope.throwIfFailed();
    return combine(a.get(), b.get());
}
@@BLOCK@@
try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    var inventory = scope.fork(this::fetchInventory);
    var price = scope.fork(this::fetchPrice);
    scope.joinUntil(deadline);
    scope.throwIfFailed();
    return combine(inventory.get(), price.get());
}
@@BLOCK@@
Future<User> user = executor.submit(this::fetchUser);
Future<List<Order>> orders = executor.submit(this::fetchOrders);