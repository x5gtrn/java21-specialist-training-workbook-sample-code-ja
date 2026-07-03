package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.atomic.AtomicInteger;
public final class Q87AtomicOperations implements Sample {
    public String chapter(){return "07_Concurrency_Threading";}
    public int problem(){return 87;}
    public String title(){return "並行処理におけるアトミック操作";}
    public void run() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger();
        Thread[] ts = new Thread[8];
        for (int i=0;i<ts.length;i++){ ts[i]=new Thread(()->{ for(int j=0;j<10000;j++) counter.incrementAndGet(); }); ts[i].start(); }
        for (Thread t: ts) t.join();
        System.out.println("AtomicInteger 合計 = " + counter.get() + "（期待 80000）");
        System.out.println("compareAndSet(80000, 0) = " + counter.compareAndSet(80000, 0) + " -> " + counter.get());
    }
}
