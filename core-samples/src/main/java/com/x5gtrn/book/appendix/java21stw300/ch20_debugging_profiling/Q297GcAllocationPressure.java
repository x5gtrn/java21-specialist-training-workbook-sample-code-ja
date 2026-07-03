package com.x5gtrn.book.appendix.java21stw300.ch20_debugging_profiling;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
public final class Q297GcAllocationPressure implements Sample {
    public String chapter(){return "20_Debugging_Profiling";}
    public int problem(){return 297;}
    public String title(){return "GC ログから見る割り当て圧";}
    public void run(){
        List<GarbageCollectorMXBean> gcs = ManagementFactory.getGarbageCollectorMXBeans();
        long before = totalCollections(gcs);
        // 大量の短命オブジェクトを割り当てて GC 圧（allocation rate）を高める
        long sink = 0;
        for (int i=0;i<2_000_000;i++){ byte[] tmp = new byte[64]; sink += tmp.length; }
        long after = totalCollections(gcs);
        System.out.println("sink=" + sink);
        System.out.println("GC 回数 before=" + before + " after=" + after + " (差分=" + (after-before) + ")");
        System.out.println("GC ログ（-Xlog:gc*）ではこの割り当て圧が Young GC 頻度として現れる");
    }
    private long totalCollections(List<GarbageCollectorMXBean> gcs){
        long n=0; for (GarbageCollectorMXBean g : gcs){ long c=g.getCollectionCount(); if (c>0) n+=c; } return n;
    }
}
