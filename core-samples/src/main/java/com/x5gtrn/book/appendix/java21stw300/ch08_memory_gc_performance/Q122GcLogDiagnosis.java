package com.x5gtrn.book.appendix.java21stw300.ch08_memory_gc_performance;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.lang.management.ManagementFactory;
public final class Q122GcLogDiagnosis implements Sample {
    public String chapter(){ return "08_Memory_GC_Performance"; }
    public int problem(){ return 122; }
    public String title(){ return "GC ログと原因切り分け"; }
    public void run(){
        var heap = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        System.out.printf("現在ヒープ: used=%dMB / committed=%dMB / max=%dMB%n",
                heap.getUsed() >> 20, heap.getCommitted() >> 20, heap.getMax() >> 20);
        System.out.println("GCログ有効化: -Xlog:gc*:file=gc.log:time,uptime,level,tags");
        System.out.println("読み方の要点:");
        System.out.println("  ・頻繁な Young GC + 低い回収率 → アロケーションレート過大 or Eden 過小");
        System.out.println("  ・Full GC 連発 + Old 高止まり → メモリリーク or ヒープ不足");
        System.out.println("  ・長い Pause → リージョン/世代サイズやGCアルゴリズム選択を見直す");
        System.out.println("メモリリークの確証はヒープダンプ(jmap)+MATで到達可能な保持ツリーを解析して得る");
    }
}
