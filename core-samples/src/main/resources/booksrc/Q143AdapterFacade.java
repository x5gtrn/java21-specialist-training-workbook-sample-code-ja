interface ShippingPort {
    ShipmentId createShipment(ShipmentRequest request);
}

class VendorShippingAdapter implements ShippingPort {
    private final VendorClient client;

    public ShipmentId createShipment(ShipmentRequest request) {
        VendorCreateRequest vendorRequest = mapToVendor(request);
        VendorCreateResponse response = client.create(vendorRequest);
        return new ShipmentId(response.id());
    }
}
@@BLOCK@@
class OrderService {
    private final ShippingPort shipping;
}
@@BLOCK@@
public record ShipmentRequest(
    OrderId orderId,
    Address destination,
    List<PackageItem> items
) {}

public record ShipmentId(String value) {}