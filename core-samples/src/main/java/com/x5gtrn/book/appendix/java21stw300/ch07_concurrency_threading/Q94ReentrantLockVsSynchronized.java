package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
public final class Q94ReentrantLockVsSynchronized implements Sample {
    public String chapter(){return "07_Concurrency_Threading";}
    public int problem(){return 94;}
    public String title(){return "ReentrantLock vs synchronized";}
    public void run() throws InterruptedException {
        // ReentrantLock は tryLock（タイムアウト付き）や割り込み対応など柔軟な制御が可能
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try { System.out.println("ロック保持中. holdCount=" + lock.getHoldCount()); }
        finally { lock.unlock(); }
        boolean acquired = lock.tryLock(100, TimeUnit.MILLISECONDS);
        System.out.println("tryLock(100ms) = " + acquired + "（synchronized では待機を諦められない）");
        if (acquired) lock.unlock();
    }
}
