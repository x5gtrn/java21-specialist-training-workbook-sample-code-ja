package com.x5gtrn.book.appendix.java21stw300.ch03_exception_handling;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 問題44: タスク内で投げられた例外は Future.get() 時に ExecutionException でラップされる。
 * 元の例外は getCause() で取得できる。
 */
public final class Q44FutureExecutionException implements Sample {

    public String chapter() { return "03_Exception_Handling"; }
    public int problem()    { return 44; }
    public String title()   { return "Future.get() と ExecutionException"; }

    public void run() throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Future<?> future = executor.submit(() -> {
                throw new IllegalStateException("task is broken");
            });
            future.get();
        } catch (ExecutionException e) {
            System.out.println("caught : " + e.getClass().getSimpleName());
            System.out.println("cause  : " + e.getCause().getClass().getSimpleName()
                    + " - " + e.getCause().getMessage());
        } finally {
            executor.shutdown();
        }
    }
}
