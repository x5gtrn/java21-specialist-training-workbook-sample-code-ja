package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
public final class Q98CompletableFutureExceptionTimeout implements Sample {
    public String chapter(){return "07_Concurrency_Threading";}
    public int problem(){return 98;}
    public String title(){return "CompletableFuture の例外処理とタイムアウト";}
    public void run() throws Exception {
        String r1 = CompletableFuture
                .<String>supplyAsync(() -> { throw new RuntimeException("処理失敗"); })
                .exceptionally(ex -> "フォールバック: " + ex.getMessage())
                .get();
        System.out.println("exceptionally -> " + r1);

        String r2 = CompletableFuture
                .supplyAsync(() -> { try { Thread.sleep(1000); } catch (InterruptedException ignored){} return "遅延結果"; })
                .orTimeout(100, TimeUnit.MILLISECONDS)
                .exceptionally(ex -> "タイムアウト: " + ex.getClass().getSimpleName())
                .get();
        System.out.println("orTimeout    -> " + r2);
    }
}
