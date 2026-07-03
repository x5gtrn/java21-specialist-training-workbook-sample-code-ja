package com.x5gtrn.book.appendix.java21stw300.ch20_debugging_profiling;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import com.sun.management.HotSpotDiagnosticMXBean;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Path;
public final class Q292HeapDumpRetainedSize implements Sample {
    public String chapter(){return "20_Debugging_Profiling";}
    public int problem(){return 292;}
    public String title(){return "ヒープダンプと保持サイズ";}
    public void run() throws Exception {
        HotSpotDiagnosticMXBean diagnostic =
                ManagementFactory.getPlatformMXBean(HotSpotDiagnosticMXBean.class);
        Path dir = Files.createTempDirectory("q292");
        Path hprof = dir.resolve("heap.hprof");             // 出力先は存在しないパスである必要がある
        try {
            diagnostic.dumpHeap(hprof.toString(), true);    // live=true で到達可能オブジェクトのみ
            System.out.println("ヒープダンプ出力: " + Files.size(hprof) + " bytes (.hprof)");
            System.out.println("保持サイズ(retained size)は Eclipse MAT 等で解析: あるオブジェクトを");
            System.out.println("解放したときに合わせて回収されるメモリ量を指す。リーク解析の要。");
        } finally {
            Files.deleteIfExists(hprof); Files.deleteIfExists(dir);
        }
    }
}
