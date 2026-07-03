package com.x5gtrn.book.appendix.java21stw300.ch20_debugging_profiling;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
public final class Q289ThreadDumpDeadlock implements Sample {
    public String chapter(){return "20_Debugging_Profiling";}
    public int problem(){return 289;}
    public String title(){return "スレッドダンプとデッドロック検出";}
    public void run() throws InterruptedException {
        ReentrantLock lockA = new ReentrantLock(), lockB = new ReentrantLock();
        CountDownLatch bothHold = new CountDownLatch(2);
        Thread t1 = new Thread(() -> lockOrder(lockA, lockB, bothHold), "worker-1");
        Thread t2 = new Thread(() -> lockOrder(lockB, lockA, bothHold), "worker-2");
        t1.start(); t2.start();
        bothHold.await();          // 双方が最初のロックを取得（この後お互いを待ってデッドロック）
        Thread.sleep(200);
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] deadlocked = bean.findDeadlockedThreads();   // JVM がデッドロックを自動検出
        if (deadlocked != null) {
            System.out.println("デッドロック検出: " + deadlocked.length + " スレッド");
            for (ThreadInfo info : bean.getThreadInfo(deadlocked))
                System.out.println("  " + info.getThreadName() + " は " + info.getLockName() + " を待機中");
        } else {
            System.out.println("デッドロック未検出");
        }
        t1.interrupt(); t2.interrupt();   // lockInterruptibly なので割り込みで解消
        t1.join(); t2.join();
        System.out.println("割り込みでロック取得を中断し、スレッドを解放");
    }
    private void lockOrder(ReentrantLock first, ReentrantLock second, CountDownLatch bothHold){
        try {
            first.lockInterruptibly();
            bothHold.countDown();
            Thread.sleep(150);
            second.lockInterruptibly();
        } catch (InterruptedException e){ Thread.currentThread().interrupt(); }
        finally {
            if (second.isHeldByCurrentThread()) second.unlock();
            if (first.isHeldByCurrentThread()) first.unlock();
        }
    }
}
