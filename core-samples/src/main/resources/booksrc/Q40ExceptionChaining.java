// ■ 例外チェーンの正しい実装
public class ServiceException extends RuntimeException {
    public ServiceException(String message, Throwable cause) {
        super(message, cause);  // POINT: cause コンストラクタ
    }
}

@Service
public class OrderService {
    public Order createOrder(OrderRequest req) {
        try {
            return orderDao.insert(req);
        } catch (SQLException e) {
            // POINT: 元の例外を cause として保持
            throw new ServiceException(
                "Order creation failed for customer: " + req.customerId(), e);
        }
    }
}

// ■ ログ出力時のスタックトレース（実際の表示形式）
// ServiceException: Order creation failed for customer: CUST-123
//     at com.app.OrderService.createOrder(OrderService.java:25)
//     at com.app.OrderController.create(OrderController.java:18)
// Caused by: java.sql.SQLException: Deadlock found when trying to get lock
//     at com.mysql.cj.jdbc.ConnectionImpl.execSQL(ConnectionImpl.java:123)
//     at com.app.OrderDao.insert(OrderDao.java:42)
//     ... (以降のフレームは省略)

// ■ cause へのアクセス
catch (ServiceException e) {
    Throwable root = e.getCause();  // POINT: 元の SQLException
    if (root instanceof SQLException sql) {
        log.error("SQL State: {}, Error Code: {}", sql.getSQLState(), sql.getErrorCode());
    }
}

// ■ addSuppressed() との違い
// cause: 例外の「原因」を表す（1つのみ、例外チェーンを構成）
// suppressed: try-with-resources の close() で発生した「追加の例外」
// → 用途が異なる。本問のケースでは cause が適切。