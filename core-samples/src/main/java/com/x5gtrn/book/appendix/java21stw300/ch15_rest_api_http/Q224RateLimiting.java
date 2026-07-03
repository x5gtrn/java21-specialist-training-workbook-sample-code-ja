package com.x5gtrn.book.appendix.java21stw300.ch15_rest_api_http;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q224RateLimiting implements Sample {
    // トークンバケット: 一定速度で補充されるトークンを消費。バーストを許しつつ平均レートを抑える。
    static final class TokenBucket {
        private final int capacity; private double tokens; private final double refillPerSec; private long last;
        TokenBucket(int capacity, double refillPerSec){ this.capacity = capacity; this.tokens = capacity;
            this.refillPerSec = refillPerSec; this.last = System.nanoTime(); }
        synchronized boolean tryAcquire(){
            long now = System.nanoTime();
            tokens = Math.min(capacity, tokens + (now - last) / 1e9 * refillPerSec);
            last = now;
            if (tokens >= 1) { tokens -= 1; return true; }
            return false;
        }
    }
    public String chapter(){ return "15_REST_API_HTTP"; }
    public int problem(){ return 224; }
    public String title(){ return "レート制限"; }
    public void run() throws InterruptedException {
        TokenBucket bucket = new TokenBucket(3, 2); // 容量3・毎秒2補充
        System.out.print("連続5リクエスト: ");
        for (int i = 0; i < 5; i++) System.out.print(bucket.tryAcquire() ? "許可 " : "429 ");
        System.out.println("\n  -> 最初の3件は許可(バケット容量)、以降は 429 Too Many Requests");
        Thread.sleep(1100); // 補充を待つ
        System.out.println("約1秒後(2トークン補充): " + (bucket.tryAcquire() ? "許可" : "429"));
        System.out.println("方式: トークン/リーキーバケット・固定/スライディングウィンドウ。分散では Redis 等で共有する");
    }
}
