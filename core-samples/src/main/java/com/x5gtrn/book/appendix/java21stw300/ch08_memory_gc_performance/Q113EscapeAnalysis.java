package com.x5gtrn.book.appendix.java21stw300.ch08_memory_gc_performance;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q113EscapeAnalysis implements Sample {
    record Vec(int x, int y) {}                       // メソッド外へ漏れない一時オブジェクト
    public String chapter(){return "08_Memory_GC_Performance";}
    public int problem(){return 113;}
    public String title(){return "エスケープ解析とスカラー置換";}
    public void run(){
        long sum = 0;
        for (int i=0;i<1_000_000;i++){
            Vec v = new Vec(i, i+1);                   // 参照が外部に逃げない（non-escaping）
            sum += v.x() + v.y();
        }
        System.out.println("合計 = " + sum);
        // JIT のエスケープ解析で「逃げない」と判定されると、スカラー置換により
        // ヒープ割り当てが消え、フィールドがローカル変数化されて GC 負荷が下がる。
        System.out.println("逃げないオブジェクトは JIT がヒープ割り当てを省略できる（スカラー置換）");
    }
}
