public record BillingCustomerId(String value) {}
public record SalesCustomerId(String value) {}
@@BLOCK@@
package billing;

public class Customer {
    private BillingCustomerId id;
    private TaxId taxId;
    private PaymentTerms paymentTerms;
}
@@BLOCK@@
package support;

public class Customer {
    private SupportCustomerId id;
    private SupportPlan plan;
    private SlaLevel slaLevel;
}
@@BLOCK@@
class CustomerTranslator {
    BillingCustomerCreated toBilling(SalesCustomerWon event) {
        // context 間の変換
    }
}