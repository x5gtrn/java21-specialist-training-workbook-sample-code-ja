package com.x5gtrn.book.appendix.java21stw300.ch02_java21_features;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.Arrays;
public final class Q21PatternSwitchNull implements Sample {
    public String chapter(){return "02_Java21_Features";}
    public int problem(){return 21;}
    public String title(){return "パターンマッチング switch と null";}
    public void run(){
        for (Object o : Arrays.asList("hello", 42, null, 3.14))
            System.out.println(classify(o));
    }
    private String classify(Object o){
        // case null を明示しないと null で NPE。従来 switch との違い。
        return switch (o) {
            case null -> "null を明示的に処理";
            case String s -> "String: " + s;
            case Integer i -> "Integer: " + i;
            default -> "other: " + o;
        };
    }
}
