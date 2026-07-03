package com.x5gtrn.book.appendix.java21stw300.ch17_testing;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
public final class Q260ConcurrentCodeTesting implements Sample {
    public String chapter(){return "17_Testing";}
    public int problem(){return 260;}
    public String title(){return "並行処理コードのテスト";}
    public void run() throws InterruptedException {
        // 並行バグは再現性が低い。全スレッドを同時に走らせて競合を炙り出すのが定石。
        int threads = 50, perThread = 1000;
        AtomicInteger safe = new AtomicInteger();
        int[] unsafe = {0};
        CountDownLatch start = new CountDownLatch(1);          // 一斉スタート用ゲート
        CountDownLatch done = new CountDownLatch(threads);
        for (int i=0;i<threads;i++){
            new Thread(() -> {
                try { start.await(); } catch (InterruptedException e){ return; }
                for (int j=0;j<perThread;j++){ safe.incrementAndGet(); unsafe[0]++; }
                done.countDown();
            }).start();
        }
        start.countDown();                                     // 全スレッドを同時解放
        done.await();
        int expected = threads * perThread;
        System.out.println("expected = " + expected);
        System.out.println("AtomicInteger = " + safe.get() + "（常に一致）");
        System.out.println("非同期な int++ = " + unsafe[0] + "（競合で一致しないことが多い → バグ検出）");
    }
}
