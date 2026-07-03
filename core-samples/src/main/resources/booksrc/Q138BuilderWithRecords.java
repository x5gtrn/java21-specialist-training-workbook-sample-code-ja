public record HttpRequest(
    String method,
    String url,
    Map<String, String> headers,
    String body,
    Duration timeout,
    boolean followRedirects
) {
    // コンパクトコンストラクタで防御的コピー
    public HttpRequest {
        headers = headers == null ? Map.of() : Map.copyOf(headers);
        Objects.requireNonNull(method, "method must not be null");
        Objects.requireNonNull(url, "url must not be null");
    }

    // POINT: static ファクトリメソッドで Builder を返す
    public static Builder builder(String method, String url) {
        return new Builder(method, url);
    }

    public static class Builder {
        private final String method;
        private final String url;
        private Map<String, String> headers = Map.of();
        private String body = "";
        private Duration timeout = Duration.ofSeconds(30);
        private boolean followRedirects = true;

        private Builder(String method, String url) {
            this.method = method;
            this.url = url;
        }

        public Builder headers(Map<String, String> h) { this.headers = h; return this; }
        public Builder body(String b) { this.body = b; return this; }
        public Builder timeout(Duration t) { this.timeout = t; return this; }
        public Builder followRedirects(boolean f) { this.followRedirects = f; return this; }

        public HttpRequest build() {
            return new HttpRequest(method, url, headers, body, timeout, followRedirects);
        }
    }
}

// POINT: 使用例: 必須パラメータは builder 引数、オプションは fluent setter
var request = HttpRequest.builder("POST", "/api/users")
    .headers(Map.of("Content-Type", "application/json"))
    .body("{\"name\":\"Alice\"}")
    .timeout(Duration.ofSeconds(10))
    .build();
// → イミュータブルな HttpRequest レコードが完成