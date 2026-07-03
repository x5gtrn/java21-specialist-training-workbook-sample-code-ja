package com.x5gtrn.book.appendix.java21stw300.ch19_logging_observability;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.HashSet;
import java.util.Set;
public final class Q284MetricsHighCardinality implements Sample {
    public String chapter(){ return "19_Logging_Observability"; }
    public int problem(){ return 284; }
    public String title(){ return "Metrics の高カーディナリティ"; }
    public void run(){
        // ラベル値の種類数だけ時系列が生成される。userId のような無限に近い値をラベルにすると爆発する。
        Set<String> seriesByUser = new HashSet<>();
        Set<String> seriesByStatus = new HashSet<>();
        for (int i = 0; i < 10_000; i++) {
            seriesByUser.add("http_requests{userId=\"" + i + "\"}");            // 悪い例: 1万系列
            seriesByStatus.add("http_requests{status=\"" + (i % 3 == 0 ? "2xx" : i % 3 == 1 ? "4xx" : "5xx") + "\"}");
        }
        System.out.println("ラベル=userId  -> 時系列数 " + seriesByUser.size() + "（カーディナリティ爆発）");
        System.out.println("ラベル=status  -> 時系列数 " + seriesByStatus.size() + "（有界で健全）");
        System.out.println("影響: 時系列DB(Prometheus等)のメモリ/保存/クエリが劣化し、監視基盤ごと不安定化");
        System.out.println("指針: ラベルは有界な低カーディナリティ次元(status/method/region)に限定。ID等は入れない");
    }
}
