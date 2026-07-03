package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.RecursiveTask;
public final class Q99ForkJoinPool implements Sample {
    static final class SumTask extends RecursiveTask<Long> {
        private final long[] a; private final int from, to;
        SumTask(long[] a, int from, int to){ this.a=a; this.from=from; this.to=to; }
        protected Long compute(){
            if (to - from <= 1000){ long s=0; for(int i=from;i<to;i++) s+=a[i]; return s; }
            int mid = (from + to) >>> 1;
            SumTask left = new SumTask(a, from, mid); left.fork();       // 分割して別スレッドへ
            SumTask right = new SumTask(a, mid, to);
            return right.compute() + left.join();                        // work-stealing で合流
        }
    }
    public String chapter(){return "07_Concurrency_Threading";}
    public int problem(){return 99;}
    public String title(){return "ForkJoinPool の work-stealing";}
    public void run(){
        long[] a = new long[100_000];
        for (int i=0;i<a.length;i++) a[i]=i+1;
        long sum = new SumTask(a, 0, a.length).invoke();
        System.out.println("1..100000 の合計 = " + sum);
    }
}
