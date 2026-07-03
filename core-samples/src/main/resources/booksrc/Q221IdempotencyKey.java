@PostMapping("/api/payments")
public ResponseEntity<PaymentResult> charge(
        @RequestHeader("Idempotency-Key") String idempotencyKey,
        @RequestBody PaymentRequest request) {

    // 1. 既に処理済みか確認
    Optional<PaymentResult> cached = idempotencyStore.find(idempotencyKey);
    if (cached.isPresent()) {
        return ResponseEntity.ok(cached.get());  // POINT: キャッシュ済み応答を返す
    }

    // 2. 新規処理
    PaymentResult result = paymentGateway.charge(request);

    // 3. 結果をキーと紐づけて保存
    idempotencyStore.save(idempotencyKey, result, Duration.ofHours(24));

    return ResponseEntity.ok(result);
}