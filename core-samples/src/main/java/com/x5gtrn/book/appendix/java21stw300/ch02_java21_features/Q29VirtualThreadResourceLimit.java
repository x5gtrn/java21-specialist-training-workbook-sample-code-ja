package com.x5gtrn.book.appendix.java21stw300.ch02_java21_features;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
public final class Q29VirtualThreadResourceLimit implements Sample {
    public String chapter(){return "02_Java21_Features";}
    public int problem(){return 29;}
    public String title(){return "仮想スレッドと外部リソース制限";}
    public void run() throws InterruptedException {
        // 仮想スレッドは大量に作れるが、DB接続など外部資源は Semaphore で同時実行数を絞る
        Semaphore limit = new Semaphore(5);
        AtomicInteger peak = new AtomicInteger();
        AtomicInteger current = new AtomicInteger();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 200; i++) {
                executor.submit(() -> {
                    limit.acquire();
                    try {
                        int now = current.incrementAndGet();
                        peak.accumulateAndGet(now, Math::max);
                        Thread.sleep(5);
                    } finally { current.decrementAndGet(); limit.release(); }
                    return null;
                });
            }
        }
        System.out.println("200 タスクを仮想スレッドで実行、同時実行の上限=5");
        System.out.println("観測された最大同時実行数 = " + peak.get() + "（<= 5 に制限）");
    }
}
