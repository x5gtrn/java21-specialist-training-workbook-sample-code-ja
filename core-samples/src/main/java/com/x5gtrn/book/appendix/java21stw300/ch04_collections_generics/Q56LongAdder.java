package com.x5gtrn.book.appendix.java21stw300.ch04_collections_generics;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * 問題56: get-then-put の複合操作はスレッドセーフでない（更新が失われる）。
 * ConcurrentHashMap + computeIfAbsent + LongAdder なら原子的にカウントできる。
 */
public final class Q56LongAdder implements Sample {

    public String chapter() { return "04_Collections_Generics"; }
    public int problem()    { return 56; }
    public String title()   { return "ConcurrentHashMap + LongAdder による安全なカウント"; }

    public void run() throws InterruptedException {
        ConcurrentHashMap<String, LongAdder> counts = new ConcurrentHashMap<>();

        int threads = 8;
        int perThread = 10_000;
        Thread[] workers = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            workers[i] = new Thread(() -> {
                for (int j = 0; j < perThread; j++) {
                    counts.computeIfAbsent("LOGIN", k -> new LongAdder()).increment();
                }
            });
            workers[i].start();
        }
        for (Thread w : workers) {
            w.join();
        }

        long expected = (long) threads * perThread;
        System.out.println("threads=" + threads + ", perThread=" + perThread);
        System.out.println("LOGIN count = " + counts.get("LOGIN").sum() + "  (expected " + expected + ")");
    }
}
