package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.locks.StampedLock;
public final class Q100StampedLockOptimisticRead implements Sample {
    private final StampedLock lock = new StampedLock();
    private int x = 1, y = 2;
    public String chapter(){return "07_Concurrency_Threading";}
    public int problem(){return 100;}
    public String title(){return "StampedLock の楽観的読み取り";}
    public void run(){
        long ws = lock.writeLock();
        try { x = 10; y = 20; } finally { lock.unlockWrite(ws); }
        System.out.println("楽観的読み取り結果 = " + readOptimistically());
    }
    private int readOptimistically(){
        long stamp = lock.tryOptimisticRead();   // ロックを取らずに読む
        int cx = x, cy = y;
        if (!lock.validate(stamp)) {              // 途中で書き込みがあれば読み直す
            stamp = lock.readLock();
            try { cx = x; cy = y; } finally { lock.unlockRead(stamp); }
        }
        return cx + cy;
    }
}
