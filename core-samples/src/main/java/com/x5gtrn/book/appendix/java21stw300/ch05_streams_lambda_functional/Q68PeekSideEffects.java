package com.x5gtrn.book.appendix.java21stw300.ch05_streams_lambda_functional;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
public final class Q68PeekSideEffects implements Sample {
    public String chapter(){return "05_Streams_Lambda_Functional";}
    public int problem(){return 68;}
    public String title(){return "Stream.peek と副作用の使いどころ";}
    public void run(){
        // peek はデバッグ／ログ向け。中間操作なので終端操作が無いと実行されない。
        List<Integer> result = List.of(1,2,3,4,5).stream()
                .peek(n -> System.out.println("  before filter: " + n))
                .filter(n -> n % 2 == 0)
                .peek(n -> System.out.println("    after filter: " + n))
                .map(n -> n * 10)
                .toList();
        System.out.println("result = " + result);
    }
}
