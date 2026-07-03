package com.x5gtrn.book.appendix.java21stw300.ch05_streams_lambda_functional;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.stream.IntStream;
public final class Q74ParallelStreamPitfalls implements Sample {
    public String chapter(){return "05_Streams_Lambda_Functional";}
    public int problem(){return 74;}
    public String title(){return "並列ストリームの落とし穴";}
    public void run(){
        // 正しい並列集計: 副作用のない reduce/sum を使う
        long sum = IntStream.rangeClosed(1, 1_000_000).parallel().asLongStream().sum();
        System.out.println("並列 sum(1..1,000,000) = " + sum);
        // 共有可変状態（例: ArrayList への add）を並列 forEach で行うと
        // データ競合で要素が失われたり例外になる。順序も保証されない。
        System.out.println("注意: 共有可変状態を parallel().forEach で更新するのは不可（reduce/collect を使う）");
    }
}
