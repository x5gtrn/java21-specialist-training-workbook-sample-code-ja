// ■ カスタム JFR イベント（アプリケーション固有のイベントを記録）
@jdk.jfr.Event
@jdk.jfr.Category("Application")
@jdk.jfr.Label("Order Processing")
public class OrderProcessingEvent extends jdk.jfr.Event {
    @jdk.jfr.Label("Order ID") public String orderId;
    @jdk.jfr.Label("Duration (ms)") public long durationMs;
}

// 使用
OrderProcessingEvent event = new OrderProcessingEvent();
event.begin();
processOrder(order);
event.orderId = order.id();
event.durationMs = event.getDuration().toMillis();
event.commit();  // POINT: JFR 記録に追加