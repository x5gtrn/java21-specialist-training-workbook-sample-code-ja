package com.x5gtrn.book.appendix.java21stw300.ch05_streams_lambda_functional;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
import java.util.stream.Collectors;
public final class Q70CollectorsTeeing implements Sample {
    record Stats(long count, int sum, double average) {}
    public String chapter(){return "05_Streams_Lambda_Functional";}
    public int problem(){return 70;}
    public String title(){return "Collectors.teeing による二重集計";}
    public void run(){
        // teeing: 2 つの Collector を同時に適用し、結果を統合（1 パスで平均を算出）
        Stats stats = List.of(10, 20, 30, 40).stream().collect(Collectors.teeing(
                Collectors.counting(),
                Collectors.summingInt(Integer::intValue),
                (count, sum) -> new Stats(count, sum, (double) sum / count)));
        System.out.println(stats);
    }
}
