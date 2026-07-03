package com.x5gtrn.book.appendix.java21stw300.ch02_java21_features;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
public final class Q27VirtualThreadSpringBoot implements Sample {
    public String chapter(){ return "02_Java21_Features"; }
    public int problem(){ return 27; }
    public String title(){ return "仮想スレッド × Spring Boot 統合"; }
    public void run() throws InterruptedException {
        // Spring Boot 3.2+ は spring.threads.virtual.enabled=true でリクエスト処理を仮想スレッド化できる。
        // その中核が newVirtualThreadPerTaskExecutor（タスクごとに軽量な仮想スレッドを割当）。
        long start = System.currentTimeMillis();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 1000).forEach(i -> executor.submit(() -> {
                try { Thread.sleep(100); } catch (InterruptedException ignored) {} // I/O待ちを模擬
                return i;
            }));
        } // close で全タスク完了待ち
        System.out.println("1000件のI/O待ちタスクを仮想スレッドで処理: " + (System.currentTimeMillis()-start) + " ms");
        System.out.println("従来のプールでは1000スレッドは重いが、仮想スレッドはブロッキング中に土台スレッドを解放する");
        System.out.println("注意: synchronized による長時間ブロックは pinning を招くため ReentrantLock を推奨");
    }
}
