package com.x5gtrn.book.appendix.java21stw300.ch08_memory_gc_performance;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.ArrayDeque;
import java.util.Deque;
public final class Q123ObjectPooling implements Sample {
    // 生成コストの高いオブジェクトを使い回すプール（借用→返却）
    static final class BufferPool {
        private final Deque<byte[]> idle = new ArrayDeque<>();
        private int created = 0;
        byte[] borrow(){ byte[] b = idle.poll(); if (b == null){ created++; b = new byte[1024]; } return b; }
        void giveBack(byte[] b){ idle.push(b); }
        int created(){ return created; }
    }
    public String chapter(){return "08_Memory_GC_Performance";}
    public int problem(){return 123;}
    public String title(){return "オブジェクトプール化の判断";}
    public void run(){
        BufferPool pool = new BufferPool();
        for (int i=0;i<100;i++){ byte[] b = pool.borrow(); /* 使用 */ pool.giveBack(b); }
        System.out.println("100回借用したが実際に生成したのは " + pool.created() + " 個（再利用）");
        // 現代のGCでは短命オブジェクトは安価。プール化はGC/割り当てがボトルネックの時のみ有効で、
        // 誤用は解放漏れやスレッド安全性の複雑化を招く。
        System.out.println("注意: プール化は生成が本当に高価な場合の最適化。安易な適用は逆効果");
    }
}
