package com.x5gtrn.book.appendix.java21stw300.ch05_streams_lambda_functional;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
public final class Q58MethodReferences implements Sample {
    public String chapter(){return "05_Streams_Lambda_Functional";}
    public int problem(){return 58;}
    public String title(){return "メソッド参照の種類";}
    public void run(){
        Function<String,Integer> staticRef = Integer::parseInt;          // 静的
        Supplier<String> boundRef = "hello"::toUpperCase;                // 特定インスタンス束縛
        Function<String,Integer> unboundRef = String::length;           // 型の任意インスタンス
        Supplier<StringBuilder> ctorRef = StringBuilder::new;           // コンストラクタ
        System.out.println("static  Integer::parseInt(\"42\") = " + staticRef.apply("42"));
        System.out.println("bound   \"hello\"::toUpperCase   = " + boundRef.get());
        System.out.println("unbound String::length(\"abc\")  = " + unboundRef.apply("abc"));
        System.out.println("ctor    StringBuilder::new       = " + ctorRef.get().append("x"));
        System.out.println("unbound で長さ順ソート -> " + List.of("bb","a","ccc").stream()
                .sorted(java.util.Comparator.comparingInt(String::length)).toList());
    }
}
