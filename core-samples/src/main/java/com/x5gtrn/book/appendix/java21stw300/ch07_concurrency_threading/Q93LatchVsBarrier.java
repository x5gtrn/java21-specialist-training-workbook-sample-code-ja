package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.CountDownLatch;
public final class Q93LatchVsBarrier implements Sample {
    public String chapter(){return "07_Concurrency_Threading";}
    public int problem(){return 93;}
    public String title(){return "CountDownLatch vs CyclicBarrier";}
    public void run() throws InterruptedException {
        // CountDownLatch: N 個の完了を 1 回だけ待つ（再利用不可）
        int workers = 3;
        CountDownLatch done = new CountDownLatch(workers);
        for (int i=0;i<workers;i++){ final int id=i; new Thread(() -> { System.out.println("worker " + id + " 完了"); done.countDown(); }).start(); }
        done.await();
        System.out.println("全 worker の完了を待って合流（CyclicBarrier は繰り返し同期に使い、再利用できる点が違い）");
    }
}
