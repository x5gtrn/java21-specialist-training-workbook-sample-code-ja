package com.x5gtrn.book.appendix.java21stw300.ch05_streams_lambda_functional;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
public final class Q62FlatMap implements Sample {
    public String chapter(){return "05_Streams_Lambda_Functional";}
    public int problem(){return 62;}
    public String title(){return "flatMap によるネストコレクションの平坦化";}
    public void run(){
        List<List<Integer>> nested = List.of(List.of(1,2), List.of(3,4,5), List.of(6));
        List<Integer> flat = nested.stream().flatMap(List::stream).toList();
        System.out.println("平坦化 -> " + flat);
        List<String> words = List.of("hello", "world");
        System.out.println("文字へ分解 -> " + words.stream()
                .flatMap(w -> w.chars().mapToObj(c -> String.valueOf((char)c))).distinct().toList());
    }
}
