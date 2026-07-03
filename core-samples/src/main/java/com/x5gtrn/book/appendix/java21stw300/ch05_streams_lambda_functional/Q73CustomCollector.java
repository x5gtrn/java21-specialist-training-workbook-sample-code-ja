package com.x5gtrn.book.appendix.java21stw300.ch05_streams_lambda_functional;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
import java.util.stream.Collector;
public final class Q73CustomCollector implements Sample {
    public String chapter(){return "05_Streams_Lambda_Functional";}
    public int problem(){return 73;}
    public String title(){return "カスタム Collector（Collector.of）";}
    public void run(){
        // supplier / accumulator / combiner / finisher を指定した自作 Collector
        Collector<String, StringBuilder, String> csv = Collector.of(
                StringBuilder::new,
                (sb, s) -> { if (sb.length() > 0) sb.append(", "); sb.append(s); },
                StringBuilder::append,
                StringBuilder::toString);
        String result = List.of("apple", "banana", "cherry").stream().collect(csv);
        System.out.println("custom collector -> " + result);
    }
}
