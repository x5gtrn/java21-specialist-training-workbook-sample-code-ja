package com.x5gtrn.book.appendix.java21stw300.ch05_streams_lambda_functional;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
import java.util.stream.Stream;
public final class Q69MapMultiVsFlatMap implements Sample {
    public String chapter(){return "05_Streams_Lambda_Functional";}
    public int problem(){return 69;}
    public String title(){return "mapMulti と flatMap の使い分け";}
    public void run(){
        // mapMulti: 各要素から 0..N 個を Consumer へ push（中間 Stream を作らない）
        List<Integer> expanded = Stream.of(1, 2, 3)
                .<Integer>mapMulti((n, downstream) -> { for (int i = 0; i < n; i++) downstream.accept(n); })
                .toList();
        System.out.println("mapMulti (n を n 回) -> " + expanded);
        // 同じ結果を flatMap で（要素ごとに Stream を生成）
        List<Integer> viaFlatMap = Stream.of(1, 2, 3)
                .flatMap(n -> Stream.generate(() -> n).limit(n)).toList();
        System.out.println("flatMap 同等         -> " + viaFlatMap);
    }
}
