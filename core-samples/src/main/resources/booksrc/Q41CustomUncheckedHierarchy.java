// ドメイン例外の基底クラス
public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}

// 具体的なドメイン例外
public class OrderNotFoundException extends DomainException {
    public OrderNotFoundException(String orderId) {
        super("Order not found: " + orderId);
    }
}

public class InsufficientStockException extends DomainException {
    public InsufficientStockException(String productId, int requested, int available) {
        super("Insufficient stock for " + productId +
              ": requested=" + requested + ", available=" + available);
    }
}

// サービス層: throws 宣言不要
public class OrderService {
    public Order findOrder(String orderId) {  // ← throws 不要
        return repository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}

// グローバル例外ハンドラ（Spring Boot 例）
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomain(DomainException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }
}