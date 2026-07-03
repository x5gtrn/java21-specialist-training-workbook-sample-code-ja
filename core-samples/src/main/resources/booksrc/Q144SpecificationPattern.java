interface Specification<T> {
    boolean isSatisfiedBy(T candidate);

    default Specification<T> and(Specification<T> other) {
        return candidate -> this.isSatisfiedBy(candidate)
            && other.isSatisfiedBy(candidate);
    }
}
@@BLOCK@@
class GoldCustomerSpec implements Specification<Customer> {
    public boolean isSatisfiedBy(Customer customer) {
        return customer.tier() == Tier.GOLD;
    }
}
@@BLOCK@@
Specification<Order> eligible =
    new GoldCustomerOrderSpec()
        .and(new CampaignPeriodSpec(clock))
        .and(new ProductCategorySpec(Category.BOOKS));
@@BLOCK@@
if (customer.tier() == GOLD && product.category() == BOOKS
    && history.total() > 100_000 && now.isBefore(campaignEnd)) {
    // 割引
}
@@BLOCK@@
record ActiveCampaignSpec(Clock clock) implements Specification<Campaign> {
    public boolean isSatisfiedBy(Campaign campaign) {
        Instant now = Instant.now(clock);
        return !now.isBefore(campaign.startsAt())
            && now.isBefore(campaign.endsAt());
    }
}
@@BLOCK@@
class DiscountPolicy {
    boolean eligible(Order order) {
        return eligibilitySpec.isSatisfiedBy(order);
    }
}