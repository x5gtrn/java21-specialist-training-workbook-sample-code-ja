record OrderSummary(long count, BigDecimal totalRevenue) {}

OrderSummary summary = orders.stream()
        .collect(Collectors.teeing(
                Collectors.counting(),
                Collectors.reducing(
                        BigDecimal.ZERO,
                        Order::amount,
                        BigDecimal::add),
                OrderSummary::new));
@@BLOCK@@
Collectors.teeing(
        downstream1,
        downstream2,
        (result1, result2) -> combinedResult
)
@@BLOCK@@
long[] count = {0};
BigDecimal[] total = {BigDecimal.ZERO};

orders.stream().forEach(order -> {
    count[0]++;
    total[0] = total[0].add(order.amount());
});
@@BLOCK@@
record QualitySummary(long invalidCount, long validCount) {}

QualitySummary quality = orders.stream()
        .collect(Collectors.teeing(
                Collectors.filtering(order -> !order.isValid(), Collectors.counting()),
                Collectors.filtering(Order::isValid, Collectors.counting()),
                QualitySummary::new));
@@BLOCK@@
Summary summary = stream.collect(Collectors.teeing(
        collectorA,
        collectorB,
        Summary::new
));
@@BLOCK@@
OrderSummary summary = orders.stream()
        .collect(Collectors.teeing(
                Collectors.counting(),
                Collectors.mapping(Order::amount,
                        Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)),
                OrderSummary::new));