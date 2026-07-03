package com.x5gtrn.book.appendix.java21stw300.ch08_memory_gc_performance;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
public final class Q120G1Fragmentation implements Sample {
    public String chapter(){ return "08_Memory_GC_Performance"; }
    public int problem(){ return 120; }
    public String title(){ return "G1 ガベージコレクタとフラグメンテーション"; }
    public void run(){
        System.out.println("稼働中の GC:");
        for (GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans())
            System.out.println("  " + gc.getName() + " (回数=" + gc.getCollectionCount() + ")");
        System.out.println("G1 はヒープを固定サイズの Region に分割し、回収効率の高い領域から回収する");
        System.out.println("Humongous: Region サイズの半分超のオブジェクトは連続 Region を占有し断片化の原因になる");
        System.out.println("対策: 巨大配列の分割 / -XX:G1HeapRegionSize 調整 / Mixed GC で断片化を緩和");
    }
}
