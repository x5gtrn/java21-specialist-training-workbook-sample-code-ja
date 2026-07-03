package com.x5gtrn.book.appendix.java21stw300.ch05_streams_lambda_functional;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public final class Q60ToListVsCollectorsToList implements Sample {
    public String chapter(){return "05_Streams_Lambda_Functional";}
    public int problem(){return 60;}
    public String title(){return "Stream.toList() と Collectors.toList() の違い";}
    public void run(){
        List<Integer> a = Stream.of(1,2,3).toList();                  // 不変（変更不可）
        List<Integer> b = Stream.of(1,2,3).collect(Collectors.toList()); // 実装依存だが通常は変更可能
        System.out.println("Stream.toList()         = " + a);
        try { a.add(4); } catch (UnsupportedOperationException e){ System.out.println("  add -> " + e.getClass().getSimpleName() + "（不変）"); }
        b.add(4);
        System.out.println("Collectors.toList()+add = " + b);
    }
}
