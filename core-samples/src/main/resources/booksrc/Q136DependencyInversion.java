// NG: DIP 違反: 上位が下位の具象クラスに直接依存
class OrderService {
    private final PostgresOrderRepository repo;  // POINT: 具象クラスに依存
    private final SmtpEmailSender sender;  // POINT: 具象クラスに依存
    OrderService() {
        this.repo = new PostgresOrderRepository();  // テスト時に DB 必要
        this.sender = new SmtpEmailSender();  // テスト時に SMTP 必要
    }
}

// OK: DIP 適用: 両者が抽象（インターフェース）に依存
interface OrderRepository { void save(Order order); }
interface NotificationSender { void send(String to, String msg); }

class OrderService {
    private final OrderRepository repo;  // POINT: 抽象に依存
    private final NotificationSender sender;  // POINT: 抽象に依存
    OrderService(OrderRepository repo, NotificationSender sender) {
        this.repo = repo;
        this.sender = sender;
    }
}

// 実装（低水準モジュール）も抽象を実装
class PostgresOrderRepository implements OrderRepository { /* 実装省略 */ }
class SmtpEmailSender implements NotificationSender { /* 実装省略 */ }

// テスト時: モックを注入
var service = new OrderService(mockRepo, mockSender);  // DB/SMTP 不要