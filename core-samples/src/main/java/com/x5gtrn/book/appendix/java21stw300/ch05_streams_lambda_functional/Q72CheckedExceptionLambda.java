package com.x5gtrn.book.appendix.java21stw300.ch05_streams_lambda_functional;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
import java.util.function.Function;
public final class Q72CheckedExceptionLambda implements Sample {
    @FunctionalInterface interface CheckedFunction<T,R> { R apply(T t) throws Exception; }
    // チェック例外を投げるラムダを非チェック例外にラップして Stream で使えるようにする
    static <T,R> Function<T,R> unchecked(CheckedFunction<T,R> f){
        return t -> { try { return f.apply(t); } catch (Exception e){ throw new RuntimeException(e); } };
    }
    static int parseStrict(String s) throws Exception {
        if (!s.matches("\\d+")) throw new Exception("not a number: " + s);
        return Integer.parseInt(s);
    }
    public String chapter(){return "05_Streams_Lambda_Functional";}
    public int problem(){return 72;}
    public String title(){return "チェック例外を投げるラムダの設計";}
    public void run(){
        List<Integer> nums = List.of("1","2","3").stream().map(unchecked(Q72CheckedExceptionLambda::parseStrict)).toList();
        System.out.println("変換成功 -> " + nums);
        try { List.of("1","x").stream().map(unchecked(Q72CheckedExceptionLambda::parseStrict)).toList(); }
        catch (RuntimeException e){ System.out.println("失敗要素で例外 -> " + e.getCause().getMessage()); }
    }
}
