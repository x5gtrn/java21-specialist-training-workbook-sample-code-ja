package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 問題96: スレッドプールとキューが満杯のとき、AbortPolicy は新規タスクを
 * RejectedExecutionException で拒否する。
 */
public final class Q96RejectedExecution implements Sample {

    public String chapter() { return "07_Concurrency_Threading"; }
    public int problem()    { return 96; }
    public String title()   { return "ThreadPoolExecutor の飽和ポリシー（AbortPolicy）"; }

    public void run() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 1, 0L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),               // キュー容量 1
                new ThreadPoolExecutor.AbortPolicy());     // 満杯なら拒否

        int submitted = 0;
        int rejected = 0;
        for (int i = 0; i < 5; i++) {
            try {
                final int id = i;
                executor.execute(() -> {
                    try { Thread.sleep(200); } catch (InterruptedException ignored) {}
                });
                submitted++;
                System.out.println("submit #" + id + " : accepted");
            } catch (RejectedExecutionException e) {
                rejected++;
                System.out.println("submit #" + i + " : " + e.getClass().getSimpleName());
            }
        }
        executor.shutdownNow();
        System.out.println("accepted=" + submitted + ", rejected=" + rejected);
    }
}
