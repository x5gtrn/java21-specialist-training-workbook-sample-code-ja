// Orchestration 方式（中央の Saga Orchestrator が制御）
public class OrderSagaOrchestrator {
    public void execute(CreateOrderCommand cmd) {
        OrderId orderId = null;
        try {
            orderId = orderService.create(cmd);
            paymentService.charge(cmd.paymentInfo());
            inventoryService.reserve(cmd.items());
        } catch (InventoryException e) {
            // 在庫確保に失敗 → 確定済みの決済と注文を補償で打ち消す
            paymentService.refund(cmd.paymentInfo());  // 補償: 決済を取り消す
            orderService.cancel(orderId);  // 補償: 注文を取り消す
        }
    }
}