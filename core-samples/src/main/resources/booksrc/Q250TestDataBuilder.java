class OrderTestBuilder {
    private Customer customer = TestCustomers.defaultCustomer();
    private List<OrderLine> lines = List.of(TestOrderLines.defaultLine());
    private OrderStatus status = OrderStatus.NEW;

    OrderTestBuilder withStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    Order build() {
        return new Order(customer, lines, status);
    }
}
@@BLOCK@@
Order canceled = anOrder()
    .withStatus(OrderStatus.CANCELED)
    .build();
@@BLOCK@@
Order order = new Order(
    new Customer("c1", "Alice", "..."),
    List.of(new OrderLine("sku", 1, new BigDecimal("100"))),
    OrderStatus.CANCELED,
    Instant.parse("2026-01-01T00:00:00Z"),
    new ShippingAddress("Tokyo")
);
@@BLOCK@@
static OrderTestBuilder anOrder() {
    return new OrderTestBuilder();
}

@Test
void canceledOrderCannotBePaid() {
    Order order = anOrder().withStatus(CANCELED).build();
    assertThatThrownBy(() -> order.pay())
        .isInstanceOf(InvalidOrderStateException.class);
}
@@BLOCK@@
User user = aUser()
    .withInvalidEmail()
    .build();