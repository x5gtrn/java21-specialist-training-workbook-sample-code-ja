package com.x5gtrn.book.appendix.java21stw300.ch19_logging_observability;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.LinkedHashMap;
import java.util.Map;
public final class Q283MicrometerMetrics implements Sample {
    // メトリクスの基本型: Counter（単調増加）と Timer（回数+合計時間）。Micrometer の考え方を最小実装で示す。
    static final class Registry {
        final Map<String,Long> counters = new LinkedHashMap<>();
        final Map<String,long[]> timers = new LinkedHashMap<>(); // [count, totalMillis]
        void count(String name){ counters.merge(name, 1L, Long::sum); }
        void time(String name, long ms){ timers.computeIfAbsent(name, k -> new long[2]); long[] t=timers.get(name); t[0]++; t[1]+=ms; }
    }
    public String chapter(){return "19_Logging_Observability";}
    public int problem(){return 283;}
    public String title(){return "Micrometer メトリクス";}
    public void run(){
        Registry reg = new Registry();
        for (int i=0;i<5;i++){ reg.count("http.requests"); reg.time("http.latency", 10L + i); }
        System.out.println("Counter http.requests = " + reg.counters.get("http.requests"));
        long[] t = reg.timers.get("http.latency");
        System.out.printf("Timer   http.latency  = count %d, avg %.1f ms%n", t[0], (double)t[1]/t[0]);
        System.out.println("Micrometer は Prometheus/Datadog 等へ同一APIで公開する『計測のSLF4J』的存在");
    }
}
