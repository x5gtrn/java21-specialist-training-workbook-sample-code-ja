package com.x5gtrn.book.appendix.java21stw300.ch20_debugging_profiling;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.Arrays;
public final class Q298DbLatencyProfiling implements Sample {
    public String chapter(){ return "20_Debugging_Profiling"; }
    public int problem(){ return 298; }
    public String title(){ return "DB レイテンシのプロファイリング"; }
    public void run(){
        // 平均だけ見ると尾側の遅延を見逃す。パーセンタイル(p50/p95/p99)で分布を把握する。
        long[] latencies = {5,6,5,7,6,5,8,6,120,7,6,5,9,6,7,200,6,5,7,6}; // ms（時々スパイク）
        long[] sorted = latencies.clone(); Arrays.sort(sorted);
        System.out.printf("平均 = %.1f ms / p50 = %d / p95 = %d / p99 = %d%n",
                Arrays.stream(latencies).average().orElse(0),
                pct(sorted,50), pct(sorted,95), pct(sorted,99));
        System.out.println("平均は低くても p99 が高い＝一部リクエストが極端に遅い（尾側遅延）");
        System.out.println("切り分け: スロークエリログ/EXPLAINで実行計画確認、インデックス欠如やN+1、ロック待ちを疑う");
        System.out.println("アプリ側: コネクションプール枯渇・往復回数(チャッティ)・フェッチ量も計測する");
    }
    private long pct(long[] sorted, int p){ int idx = (int)Math.ceil(p/100.0*sorted.length)-1;
        return sorted[Math.max(0, Math.min(sorted.length-1, idx))]; }
}
