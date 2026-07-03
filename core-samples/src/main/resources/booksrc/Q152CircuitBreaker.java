@Service
public class PaymentService {
    private final PaymentGateway gateway;

    @CircuitBreaker(name = "payment", fallbackMethod = "fallbackCharge")
    @TimeLimiter(name = "payment")
    @Retry(name = "payment")
    public CompletableFuture<PaymentResult> charge(BigDecimal amount) {
        return CompletableFuture.supplyAsync(() -> gateway.charge(amount));
    }

    // フォールバック: Circuit Open 時またはタイムアウト時に呼ばれる
    public CompletableFuture<PaymentResult> fallbackCharge(
            BigDecimal amount, Exception ex) {
        log.warn("Payment gateway unavailable, queuing for retry", ex);
        return CompletableFuture.completedFuture(
            new PaymentResult(false, "Service temporarily unavailable"));
    }
}

// 設定ファイル: application.yml
// resilience4j.circuitbreaker.instances.payment:
// failure-rate-threshold: 50         # 失敗率50%で Open
// wait-duration-in-open-state: 30s   # Open 状態の待機時間
// permitted-number-of-calls-in-half-open-state: 5  # Half-Open での試行数
// sliding-window-size: 10            # 直近10リクエストで判定