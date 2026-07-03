// OK: コンストラクタインジェクション（推奨）
@Service
public class OrderService {
    private final PaymentGateway paymentGateway;  // POINT: final
    private final OrderRepository orderRepository;  // POINT: final
    private final NotificationService notificationService;  // POINT: final

    // Spring Boot: コンストラクタが1つなら @Autowired 不要
    public OrderService(PaymentGateway paymentGateway,
                         OrderRepository orderRepository,
                         NotificationService notificationService) {
        this.paymentGateway = paymentGateway;
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
    }
    // → 不変、スレッドセーフ、依存が明確
}

// テスト時: new で簡単にモック注入
@Test
void shouldProcessOrder() {
    var service = new OrderService(mockGateway, mockRepo, mockNotification);
    // POINT: Spring コンテナ不要、リフレクション不要
}

// NG: フィールドインジェクション（非推奨）
@Service
public class OrderService {
    @Autowired private PaymentGateway paymentGateway;  // final 不可
    @Autowired private OrderRepository orderRepository;
    // → ミュータブル、テスト時にリフレクション必要
    // → NullPointerException のリスク（new OrderService()すると null）
}

// NG: コンストラクタ循環依存は起動時に検出される
@Service
class AService {
    AService(BService bService) {}
}
@Service
class BService {
    BService(AService aService) {}
}
// → BeanCurrentlyInCreationException などで起動に失敗し、設計上の循環依存が早期に分かる