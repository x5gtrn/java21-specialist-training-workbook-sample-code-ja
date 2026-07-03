if (amount > 0) { discount++; }
@@BLOCK@@
if (amount >= 0) { discount++; }
@@BLOCK@@
@Test
void calculatesDiscount() {
    discountService.calculate(order);
}
@@BLOCK@@
int discount(Customer customer) {
    if (customer.isPremium()) {
        return 20;
    }
    return 0;
}
@@BLOCK@@
@Test
void premiumCustomerGetsDiscount() {
    assertThat(service.discount(premiumCustomer())).isEqualTo(20);
}

@Test
void normalCustomerGetsNoDiscount() {
    assertThat(service.discount(normalCustomer())).isZero();
}