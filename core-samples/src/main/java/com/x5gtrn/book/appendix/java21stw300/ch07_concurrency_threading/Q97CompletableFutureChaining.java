package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.CompletableFuture;
public final class Q97CompletableFutureChaining implements Sample {
    public String chapter(){ return "07_Concurrency_Threading"; }
    public int problem(){ return 97; }
    public String title(){ return "CompletableFuture のチェーン操作"; }
    public void run() throws Exception {
        CompletableFuture<Integer> a = CompletableFuture.supplyAsync(() -> 10);
        CompletableFuture<Integer> b = CompletableFuture.supplyAsync(() -> 20);
        Integer result = a
            .thenApply(x -> x + 1)                                   // 同期変換（値→値）
            .thenCompose(x -> CompletableFuture.supplyAsync(() -> x * 2)) // 別の非同期へ連結（Future→Future）
            .thenCombine(b, Integer::sum)                            // 2つの結果を合成
            .exceptionally(ex -> -1)                                 // 例外時のフォールバック
            .get();
        System.out.println("((10+1)*2) + 20 = " + result);
        // thenApply(値→値) と thenCompose(値→Future) の違いが頻出。ネストを避けるのが thenCompose。
        CompletableFuture<Integer> failed = CompletableFuture.<Integer>supplyAsync(() -> { throw new RuntimeException("boom"); })
            .exceptionally(ex -> {
                System.out.println("exceptionally が捕捉: " + ex.getCause().getMessage());
                return 0;
            });
        System.out.println("失敗系のフォールバック値 = " + failed.get());
    }
}
