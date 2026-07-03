// ■ レスポンスヘッダーの例
// HTTP/1.1 429 Too Many Requests
// Retry-After: 30
// X-RateLimit-Limit: 100
// X-RateLimit-Remaining: 0
// X-RateLimit-Reset: 1706097600

// ■ 主要アルゴリズム
// Token Bucket:     トークン補充方式、バースト許容、最も一般的
// Sliding Window:   時間窓カウント方式、正確だがメモリ消費
// Fixed Window:     固定間隔リセット、実装簡単、境界バースト問題あり
// Leaky Bucket:     一定レート出力、バーストを平滑化