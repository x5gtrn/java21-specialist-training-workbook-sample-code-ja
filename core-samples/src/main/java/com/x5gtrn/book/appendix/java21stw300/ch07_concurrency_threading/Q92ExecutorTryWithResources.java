package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.Executors;
public final class Q92ExecutorTryWithResources implements Sample {
    public String chapter(){return "07_Concurrency_Threading";}
    public int problem(){return 92;}
    public String title(){return "ExecutorService の終了と try-with-resources";}
    public void run() {
        // Java 19+ で ExecutorService は AutoCloseable。close() が shutdown + 待機を行う
        try (var executor = Executors.newFixedThreadPool(3)) {
            for (int i=0;i<3;i++){ final int id=i; executor.submit(() -> System.out.println("job " + id)); }
        } // ここで全タスク完了を待って自動 shutdown
        System.out.println("try-with-resources を抜けた時点で全タスクが完了している");
    }
}
