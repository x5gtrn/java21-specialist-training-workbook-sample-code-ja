// order module が公開する port
public interface OrderPlacement {
    OrderId placeOrder(PlaceOrderCommand command);
}
@@BLOCK@@
classes()
    .that().resideInAPackage("..billing..")
    .should().onlyDependOnClassesThat()
    .resideOutsideOfPackage("..order.internal..");