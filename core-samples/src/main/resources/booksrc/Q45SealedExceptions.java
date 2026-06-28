// ■ sealed 例外階層（D）
sealed abstract class ProcessingException extends RuntimeException
    permits RetryableException, FatalException {
    ProcessingException(String msg, Throwable cause) { super(msg, cause); }
    ProcessingException(String msg) { super(msg); }
}
final class RetryableException extends ProcessingException {  // POINT: E: 非チェック
    RetryableException(String msg, Throwable cause) { super(msg, cause); }
}
final class FatalException extends ProcessingException {  // POINT: E: 非チェック
    FatalException(String msg) { super(msg); }
}

// ■ ストリーム内での使用（A）
List<ProcessedOrder> results = orders.stream()
    .map(order -> {
        try {
            return Optional.of(processOrder(order));  // POINT: A: Optional でラップ
        } catch (RetryableException e) {
            retryQueue.add(order);  // 回復可能 → リトライへ
            return Optional.<ProcessedOrder>empty();  // POINT: パイプライン継続
        }
        // FatalException（E: 非チェック）→ 自然に伝播 → パイプライン停止
    })
    .filter(Optional::isPresent)
    .map(Optional::get)
    .toList();

// ■ sealed 階層でのパターンマッチング（D）
catch (ProcessingException e) {
    switch (e) {
        case RetryableException r -> scheduleRetry(r);
        case FatalException f     -> alertOps(f);
        // POINT: sealed → 網羅性保証、新しい例外型追加時にコンパイルエラー
    }
}