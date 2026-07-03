@Property
void normalizeIsIdempotent(@ForAll BigDecimal amount) {
    Money once = Money.normalize(amount);
    Money twice = Money.normalize(once.amount());
    assertThat(twice).isEqualTo(once);
}
@@BLOCK@@
@Property
void formatAndParseRoundTrip(@ForAll("validMoney") Money money) {
    String formatted = formatter.format(money);
    Money parsed = parser.parse(formatted);
    assertThat(parsed).isEqualTo(money);
}
@@BLOCK@@
@Provide
Arbitrary<BigDecimal> validAmounts() {
    return Arbitraries.bigDecimals()
        .between(BigDecimal.ZERO, new BigDecimal("1000000"))
        .ofScale(2);
}