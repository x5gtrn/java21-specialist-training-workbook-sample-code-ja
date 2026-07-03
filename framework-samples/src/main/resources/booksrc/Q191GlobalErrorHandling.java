// ■ 統一エラーレスポンス構造
record ErrorResponse(
    String code,
    String message,
    String path,
    LocalDateTime timestamp
) {}

@RestControllerAdvice
public class GlobalExceptionHandler {

    // リソース不存在
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        return new ErrorResponse("NOT_FOUND", ex.getMessage(),
                                  req.getRequestURI(), LocalDateTime.now());
    }

    // バリデーションエラー
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex,
                                           HttpServletRequest req) {
        String details = ex.getBindingResult().getFieldErrors().stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .collect(Collectors.joining("; "));
        return new ErrorResponse("VALIDATION_ERROR", details,
                                  req.getRequestURI(), LocalDateTime.now());
    }

    // ビジネスロジック例外
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleBusiness(BusinessException ex, HttpServletRequest req) {
        return new ErrorResponse(ex.getCode(), ex.getMessage(),
                                  req.getRequestURI(), LocalDateTime.now());
    }

    // 予期しない例外（最後のフォールバック）
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnexpected(Exception ex, HttpServletRequest req) {
        log.error("Unexpected error", ex);
        return new ErrorResponse("INTERNAL_ERROR", "An unexpected error occurred",
                                  req.getRequestURI(), LocalDateTime.now());
    }
}
// → すべてのコントローラで一貫した JSON 形式のエラーレスポンス
// { "code": "NOT_FOUND", "message": "User not found", "path": "/api/users/99", ... }