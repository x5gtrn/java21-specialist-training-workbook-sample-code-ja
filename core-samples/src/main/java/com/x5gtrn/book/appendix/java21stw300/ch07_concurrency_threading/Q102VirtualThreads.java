package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 問題102: 仮想スレッド（JEP 444, Java 21 正式）の3つの作り方。
 * 1) Thread.ofVirtual()  2) Executors.newVirtualThreadPerTaskExecutor()  3) Thread.startVirtualThread()
 */
public final class Q102VirtualThreads implements Sample {

    public String chapter() { return "07_Concurrency_Threading"; }
    public int problem()    { return 102; }
    public String title()   { return "仮想スレッドの3つの生成方法"; }

    public void run() throws InterruptedException {
        // 1. Thread.ofVirtual() ビルダー
        Thread vt = Thread.ofVirtual().name("worker-1").start(() ->
                System.out.println("1) ofVirtual : isVirtual=" + Thread.currentThread().isVirtual()
                        + ", name=" + Thread.currentThread().getName()));
        vt.join();

        // 2. Executors.newVirtualThreadPerTaskExecutor()（推奨）
        AtomicInteger done = new AtomicInteger();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10_000; i++) {
                executor.submit(done::incrementAndGet);
            }
        } // try-with-resources の close() で全タスク完了を待機
        System.out.println("2) executor  : completed tasks = " + done.get());

        // 3. Thread.startVirtualThread()（簡易版）
        Thread t = Thread.startVirtualThread(() ->
                System.out.println("3) startVirtualThread : isVirtual=" + Thread.currentThread().isVirtual()));
        t.join();
    }
}
