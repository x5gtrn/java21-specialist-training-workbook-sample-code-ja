package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.locks.ReentrantLock;
public final class Q103VirtualThreadPinning implements Sample {
    public String chapter(){return "07_Concurrency_Threading";}
    public int problem(){return 103;}
    public String title(){return "仮想スレッドの pinning と synchronized ブロック";}
    public void run() throws InterruptedException {
        // synchronized ブロック内でブロッキング I/O を行うとキャリアスレッドが pin される。
        // ReentrantLock を使えば pinning を避けられる（仮想スレッドがアンマウント可能）。
        ReentrantLock lock = new ReentrantLock();
        Thread vt = Thread.ofVirtual().start(() -> {
            lock.lock();
            try { System.out.println("ReentrantLock 使用: isVirtual=" + Thread.currentThread().isVirtual() + "（pin されにくい）"); }
            finally { lock.unlock(); }
        });
        vt.join();
        System.out.println("ヒント: 仮想スレッドではロックを synchronized から ReentrantLock へ置き換えると良い");
    }
}
