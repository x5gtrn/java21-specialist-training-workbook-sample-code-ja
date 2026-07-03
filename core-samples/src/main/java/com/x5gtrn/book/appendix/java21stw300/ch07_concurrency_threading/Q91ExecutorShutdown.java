package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public final class Q91ExecutorShutdown implements Sample {
    public String chapter(){return "07_Concurrency_Threading";}
    public int problem(){return 91;}
    public String title(){return "ExecutorService のシャットダウン忘れ";}
    public void run() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            for (int i=0;i<3;i++){ final int id=i; executor.submit(() -> System.out.println("task " + id + " done")); }
        } finally {
            executor.shutdown(); // 忘れると非デーモンスレッドが残り JVM が終了しない
            boolean ok = executor.awaitTermination(2, TimeUnit.SECONDS);
            System.out.println("shutdown 完了 = " + ok);
        }
    }
}
