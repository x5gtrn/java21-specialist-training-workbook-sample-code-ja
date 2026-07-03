package com.x5gtrn.book.appendix.java21stw300.ch08_memory_gc_performance;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q111StringConcatLoop implements Sample {
    public String chapter(){return "08_Memory_GC_Performance";}
    public int problem(){return 111;}
    public String title(){return "文字列連結とループ内割り当て";}
    public void run(){
        int n = 20_000;
        long t1 = System.nanoTime();
        String s = "";
        for (int i=0;i<n;i++) s += i;                 // 毎回新しい String を生成（O(n^2)）
        long naive = System.nanoTime() - t1;
        long t2 = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<n;i++) sb.append(i);           // 内部バッファを再利用（O(n)）
        long builder = System.nanoTime() - t2;
        System.out.println("length 一致 = " + (s.length()==sb.length()));
        System.out.printf("String +=       : %d ms%n", naive/1_000_000);
        System.out.printf("StringBuilder   : %d ms%n", builder/1_000_000);
    }
}
