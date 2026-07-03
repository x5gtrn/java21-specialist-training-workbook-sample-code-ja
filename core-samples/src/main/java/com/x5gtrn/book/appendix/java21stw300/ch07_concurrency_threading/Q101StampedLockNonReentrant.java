package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.locks.StampedLock;
public final class Q101StampedLockNonReentrant implements Sample {
    public String chapter(){return "07_Concurrency_Threading";}
    public int problem(){return 101;}
    public String title(){return "StampedLock の非リエントラント性";}
    public void run(){
        StampedLock lock = new StampedLock();
        long stamp = lock.writeLock();
        System.out.println("writeLock 取得. isWriteLocked=" + lock.isWriteLocked());
        lock.unlockWrite(stamp);
        System.out.println("解放後 isWriteLocked=" + lock.isWriteLocked());
        // 注意: StampedLock は非リエントラント。同一スレッドが解放前に再取得すると自己デッドロックする。
        System.out.println("同一スレッドでの再取得は自己デッドロックを招くため行わない（synchronized/ReentrantLock との違い）");
    }
}
