record User(String name, Address address) {}
record Address(String city, String zip) {}

static String cityOf(Object value) {
    return switch (value) {
        case User(String name, Address(String city, String zip)) -> city;
        default -> "unknown";
    };
}
@@BLOCK@@
static Optional<String> primaryCity(User user) {
    Address address = user.address();
    return address == null ? Optional.empty() : Optional.of(address.city());
}
@@BLOCK@@
case ApiResponse(
    Meta(String traceId, int version),
    Payload(User(String name, Address(String city, String zip)))
) -> handleDeepResponse(traceId, version, name, city, zip);
@@BLOCK@@
record Money(String currency, long amount) {}
record Payment(String id, Money total) {}

static boolean isLargeUsdPayment(Object value) {
    return switch (value) {
        case Payment(String id, Money(String currency, long amount))
                when currency.equals("USD") && amount >= 10_000 -> true;
        default -> false;
    };
}
@@BLOCK@@
static boolean isLargeUsd(Money money) {
    return money.currency().equals("USD") && money.amount() >= 10_000;
}