package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;
public final class Q90BlockingQueueOverWaitNotify implements Sample {
    public String chapter(){return "07_Concurrency_Threading";}
    public int problem(){return 90;}
    public String title(){return "wait/notify より BlockingQueue を選ぶ場面";}
    public void run() throws InterruptedException {
        // 低レベルな wait/notify は取りこぼし・偽の起床の扱いが難しい。
        // BlockingQueue は境界・待機・通知を内部で正しく処理してくれる。
        BlockingQueue<String> handoff = new LinkedBlockingQueue<>();
        Thread worker = new Thread(() -> {
            try { System.out.println("受信: " + handoff.take()); } catch (InterruptedException e){ Thread.currentThread().interrupt(); }
        });
        worker.start();
        handoff.put("task-1");
        worker.join();
        System.out.println("wait/notify を書かずに安全な受け渡しが完了");
    }
}
