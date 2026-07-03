// ■ オフセットベース（Spring Data デフォルト）
@GetMapping("/products")
public Page<Product> list(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "20") int size) {
    return productRepository.findAll(PageRequest.of(page, size, Sort.by("id")));
}
// GET /products?page=5000&size=20
// → SELECT * FROM products ORDER BY id LIMIT 20 OFFSET 100000
// → DB は 100,000 行をスキップ（大きいオフセットで遅い）

// ■ カーソルベース
@GetMapping("/products")
public CursorPage<Product> list(
        @RequestParam(required = false) Long afterId,
        @RequestParam(defaultValue = "20") int size) {
    List<Product> items = afterId == null
        ? productRepository.findTopN(size)
        : productRepository.findByIdGreaterThan(afterId, Limit.of(size));

    Long nextCursor = items.isEmpty() ? null : items.getLast().getId();
    return new CursorPage<>(items, nextCursor, items.size() == size);
}
// GET /products?afterId=1234&size=20
// → SELECT * FROM products WHERE id > 1234 ORDER BY id LIMIT 20
// → インデックス使用、常に高速

// ■ 使い分け
// オフセットベース: 管理画面、ページ番号ナビゲーション、中小規模データ
// カーソルベース:   無限スクロール、リアルタイムフィード、大規模データ、API