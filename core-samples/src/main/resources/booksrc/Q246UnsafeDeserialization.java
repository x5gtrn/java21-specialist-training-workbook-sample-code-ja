try (ObjectInputStream in = new ObjectInputStream(request.getInputStream())) {
    Object obj = in.readObject();
}
@@BLOCK@@
public record CreateOrderRequest(Long itemId, int quantity) {}
@@BLOCK@@
@PostMapping("/orders")
OrderResponse create(@Valid @RequestBody CreateOrderRequest request) {
    return service.create(request);
}
@@BLOCK@@
ObjectInputFilter filter = ObjectInputFilter.Config.createFilter(
    "com.example.SafeType;java.base/*;!*"
);
in.setObjectInputFilter(filter);