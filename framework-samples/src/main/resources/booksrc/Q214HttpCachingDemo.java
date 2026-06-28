@GetMapping("/api/products/{id}")
public ResponseEntity<Product> getProduct(@PathVariable Long id) {
    Product product = productService.findById(id);
    String etag = "\"" + product.getVersion() + "\"";

    return ResponseEntity.ok()
        .cacheControl(CacheControl.maxAge(Duration.ofHours(1)))  // 1時間キャッシュ
        .eTag(etag)  // ETag: "5"（バージョン番号等）
        .body(product);
}

// POINT: 条件付きリクエスト（クライアント → サーバー）
// GET /api/products/42
// If-None-Match: "5"         ← 前回の ETag を送信
//
// → 変更なし: 304 Not Modified（ボディなし、帯域節約）
// → 変更あり: 200 OK + 新しい ETag + 新しいボディ

// POINT: Cache-Control ディレクティブ
// max-age=3600  → 3600秒間キャッシュ利用可能
// no-cache      → キャッシュ可能だが毎回サーバーに再検証が必要
// no-store      → キャッシュ完全禁止（機密データ用）
// public/private → プロキシキャッシュ可/不可