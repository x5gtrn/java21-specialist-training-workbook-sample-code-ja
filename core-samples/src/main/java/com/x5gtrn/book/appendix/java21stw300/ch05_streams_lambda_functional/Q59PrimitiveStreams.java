package com.x5gtrn.book.appendix.java21stw300.ch05_streams_lambda_functional;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.stream.IntStream;
public final class Q59PrimitiveStreams implements Sample {
    public String chapter(){return "05_Streams_Lambda_Functional";}
    public int problem(){return 59;}
    public String title(){return "ストリームパイプラインとプリミティブストリーム";}
    public void run(){
        // IntStream はボクシングを避け、sum()/average() など数値集計を直接提供
        int sum = IntStream.rangeClosed(1, 10).sum();
        double avg = IntStream.rangeClosed(1, 10).average().orElse(0);
        int sumOfSquares = IntStream.rangeClosed(1, 5).map(n -> n * n).sum();
        System.out.println("1..10 sum = " + sum + ", average = " + avg);
        System.out.println("1..5 の二乗和 = " + sumOfSquares);
        System.out.println("boxed -> " + IntStream.rangeClosed(1,3).boxed().toList());
    }
}
