package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.atomic.AtomicInteger;
public final class Q88VolatileCompoundLimit implements Sample {
    static class Counter { volatile int value; }
    public String chapter(){return "07_Concurrency_Threading";}
    public int problem(){return 88;}
    public String title(){return "volatile と複合操作の限界";}
    public void run() throws InterruptedException {
        // volatile は可視性のみ保証。value++ は read-modify-write の複合操作でアトミックではない
        Counter c = new Counter();
        runAll(() -> { for (int j=0;j<10000;j++) c.value++; });
        System.out.println("volatile value++ 合計 = " + c.value + "（期待 80000 だが競合で下回りやすい）");
        AtomicInteger atomic = new AtomicInteger();
        runAll(() -> { for (int j=0;j<10000;j++) atomic.incrementAndGet(); });
        System.out.println("AtomicInteger 合計     = " + atomic.get() + "（常に 80000）");
    }
    private void runAll(Runnable r) throws InterruptedException {
        Thread[] ts=new Thread[8];
        for(int i=0;i<ts.length;i++){ ts[i]=new Thread(r); ts[i].start(); }
        for(Thread t:ts) t.join();
    }
}
