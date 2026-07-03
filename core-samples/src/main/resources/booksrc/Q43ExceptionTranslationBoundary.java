try {
    paymentClient.charge(request);
} catch (SocketTimeoutException e) {
    throw new PaymentGatewayUnavailableException("Payment gateway timed out", e);
}
@@BLOCK@@
@RestControllerAdvice
class ApiExceptionHandler {
    @ExceptionHandler(InvalidOrderException.class)
    ResponseEntity<ApiError> invalidOrder(InvalidOrderException e) {
        return ResponseEntity.badRequest()
                .body(new ApiError("INVALID_ORDER", e.getMessage()));
    }

    @ExceptionHandler(PaymentGatewayUnavailableException.class)
    ResponseEntity<ApiError> paymentUnavailable(PaymentGatewayUnavailableException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ApiError("PAYMENT_UNAVAILABLE", "Payment is temporarily unavailable"));
    }
}
@@BLOCK@@
record ApiError(
        String code,
        String message,
        String traceId
) {}
@@BLOCK@@
class PaymentGatewayUnavailableException extends RuntimeException {
    PaymentGatewayUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}

@RestControllerAdvice
class ApiErrors {
    @ExceptionHandler(PaymentGatewayUnavailableException.class)
    ResponseEntity<ApiError> handle(PaymentGatewayUnavailableException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ApiError("PAYMENT_UNAVAILABLE",
                        "Payment service is temporarily unavailable",
                        Trace.currentId()));
    }
}