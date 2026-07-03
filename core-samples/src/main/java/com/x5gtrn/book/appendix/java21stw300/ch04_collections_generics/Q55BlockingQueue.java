package com.x5gtrn.book.appendix.java21stw300.ch04_collections_generics;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
public final class Q55BlockingQueue implements Sample {
    public String chapter(){return "04_Collections_Generics";}
    public int problem(){return 55;}
    public String title(){return "BlockingQueue とプロデューサー・コンシューマー";}
    public void run() throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
        final int N = 5, POISON = -1;
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= N; i++) { queue.put(i); System.out.println("produce " + i); }
                queue.put(POISON);
            } catch (InterruptedException e){ Thread.currentThread().interrupt(); }
        });
        StringBuilder consumed = new StringBuilder();
        Thread consumer = new Thread(() -> {
            try {
                int v; while ((v = queue.take()) != POISON) consumed.append(v).append(" ");
            } catch (InterruptedException e){ Thread.currentThread().interrupt(); }
        });
        producer.start(); consumer.start();
        producer.join(); consumer.join();
        System.out.println("consumed = " + consumed.toString().trim());
    }
}
