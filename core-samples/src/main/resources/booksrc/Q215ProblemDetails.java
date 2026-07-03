// POINT: Spring Boot 3.x のネイティブサポート
// 設定ファイル: application.properties
// spring.mvc.problemdetails.enabled=true

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InsufficientFundsException.class)
    public ProblemDetail handleInsufficientFunds(InsufficientFundsException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
            HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        pd.setTitle("Insufficient Funds");
        pd.setType(URI.create("https://api.example.com/errors/insufficient-funds"));
        pd.setProperty("balance", ex.getBalance());  // 拡張フィールド
        return pd;
    }
}
// Spring Boot 3.x は ProblemDetail クラスをネイティブ提供