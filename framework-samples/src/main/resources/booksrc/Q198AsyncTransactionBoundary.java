@Transactional
public void placeOrder(OrderCommand command) {
    orderRepository.save(command.toOrder());
    notificationService.sendAsync(command.userId());  // 別 thread
}
@@BLOCK@@
@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
public void on(OrderPlaced event) {
    notificationService.send(event);
}
@@BLOCK@@
@Service
class OrderService {
    private final ApplicationEventPublisher events;

    @Transactional
    public void placeOrder(OrderCommand command) {
        Order order = repository.save(command.toOrder());
        events.publishEvent(new OrderPlaced(order.getId()));
    }
}

@Component
class OrderEventHandler {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    void afterCommit(OrderPlaced event) {
        notificationService.send(event.orderId());
    }
}