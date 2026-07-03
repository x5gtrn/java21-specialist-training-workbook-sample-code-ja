record RequestContext(String requestId, String userId, Instant startedAt) {}

RequestContext context = new RequestContext(requestId, userId, Instant.now());
@@BLOCK@@
class MutableContext {
    String requestId;
    String userId;
    Instant startedAt;

    void reset(String requestId, String userId, Instant startedAt) {
        this.requestId = requestId;
        this.userId = userId;
        this.startedAt = startedAt;
    }
}
@@BLOCK@@
record RequestContext(String requestId, String userId, Instant startedAt) {}

RequestContext createContext(Request request) {
    return new RequestContext(request.id(), request.userId(), Instant.now());
}
@@BLOCK@@
// プールに向く例: 高コストで数を制御すべきリソース
DataSource dataSource = connectionPool();

// プールに向きにくい例: 小さく短命な値オブジェクト
record LineItemView(String sku, int quantity) {}
@@BLOCK@@
// 短命で閉じた値なら最適化されやすい
PriceView view = new PriceView(price, currency);
render(view);

// プールに入れると寿命と所有権が複雑になる
PriceView view = pool.borrow();
try {
    view.reset(price, currency);
    render(view);
} finally {
    pool.release(view);
}