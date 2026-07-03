package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.ConcurrentHashMap;
public final class Q95ConcurrentHashMapAtomicUpdate implements Sample {
    public String chapter(){return "07_Concurrency_Threading";}
    public int problem(){return 95;}
    public String title(){return "ConcurrentHashMap のアトミック更新";}
    public void run() throws InterruptedException {
        ConcurrentHashMap<String,Integer> map = new ConcurrentHashMap<>();
        Thread[] ts = new Thread[8];
        for (int i=0;i<ts.length;i++){ ts[i]=new Thread(() -> { for(int j=0;j<10000;j++) map.merge("hits", 1, Integer::sum); }); ts[i].start(); }
        for (Thread t: ts) t.join();
        // merge / compute はキー単位でアトミック。get→put の複合操作より安全。
        System.out.println("merge によるカウント = " + map.get("hits") + "（期待 80000）");
    }
}
