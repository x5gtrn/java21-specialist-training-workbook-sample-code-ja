package com.x5gtrn.book.appendix.java21stw300.ch08_memory_gc_performance;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q124Jmh implements Sample {
    public String chapter(){ return "08_Memory_GC_Performance"; }
    public int problem(){ return 124; }
    public String title(){ return "JMH（Java Microbenchmark Harness）"; }
    public void run(){
        // 素朴な自作ベンチはあてにならない例: 結果を使わないと JIT が計算ごと消す(デッドコード除去)
        long t = System.nanoTime();
        long acc = 0;
        for (int i = 0; i < 10_000_000; i++) acc += i * 3L;
        long elapsed = (System.nanoTime() - t) / 1_000_000;
        System.out.println("素朴な計測: " + elapsed + " ms（acc=" + acc + " を使わないと最適化で消える危険）");
        System.out.println("落とし穴: ウォームアップ不足(JIT前)/定数畳み込み/デッドコード除去/オンスタック置換");
        System.out.println("JMH の対策: @Warmup/@Measurement で反復、Blackhole で結果消去を防止、@State で入力管理");
        System.out.println("結論: マイクロベンチは必ず JMH を使い、単発の System.nanoTime 計測で判断しない");
    }
}
